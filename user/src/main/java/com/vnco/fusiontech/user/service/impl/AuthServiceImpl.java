package com.vnco.fusiontech.user.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.rpc.context.AttributeContext;
import com.vnco.fusiontech.common.constant.AuthoritiesConstant;
import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.common.exception.RecordExistsException;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.common.exception.UnauthorizedException;
import com.vnco.fusiontech.common.service.PublicMailService;
import com.vnco.fusiontech.common.utils.FirebaseUtils;
import com.vnco.fusiontech.common.web.request.mail.MailRequest;
import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.entity.roles.Roles;
import com.vnco.fusiontech.user.repository.UserRepository;
import com.vnco.fusiontech.user.service.AuthService;
import com.vnco.fusiontech.user.web.rest.request.FirebaseMapper;
import com.vnco.fusiontech.user.web.rest.request.UserRequest;
import jakarta.persistence.Tuple;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.sql.ConversionException;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static FirebaseAuth auth() {
        return FirebaseAuth.getInstance();
    }

    private final FirebaseMapper mapper;
    private final PublicMailService mailService;
    private final UserRepository userRepository;

    @Override
    public UserRecord registerWithEmailProvider(UserRequest request) {
        try {
            var user = mapper.toFirebaseCreateRequest(request);
            var newUser = FirebaseAuth.getInstance().createUser(user);
            generateVerifyLink(newUser.getEmail());
            return newUser;
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
            return null;
        }
    }

    @Override
    public UserRecord registerWithGoogleProvider(String firebaseId) {
        try {
            return FirebaseAuth.getInstance().getUser(firebaseId);
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
            return null;
        }
    }

    @Override
    public String setInitialClaims(Long id, String firebaseId, Roles...roles) {
        var claims = getInitialClaims(id, roles);
        try {
            auth().setCustomUserClaims(firebaseId, claims);
            return FirebaseAuth.getInstance().createCustomToken(firebaseId, claims);
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
        }
        return null;
    }

    @Override
    public void updateProfile(User user, UserRequest request, String firebaseId) {
        var updateRequest = new UserRecord.UpdateRequest(firebaseId);
        var mapped = mapper.toFirebaseUpdateRequest(request, user, updateRequest);
        if (request.phoneNumber() != null)
            updateRequest.setPhoneNumber(FirebaseUtils.convertToE164Format(request.phoneNumber()));

        try {
            FirebaseAuth.getInstance().updateUser(mapped);
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
        }
    }

    @Override
    public void updatePassword(String password, String firebaseId) {
        UserRecord.UpdateRequest user = new UserRecord.UpdateRequest(firebaseId);
        if (password != null)
            throw new IllegalArgumentException("Invalid password: " + password);
        user.setPassword(password);
        try {
            FirebaseAuth.getInstance().updateUser(user);
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
        }
    }

    @Override
    public void deleteAccount(String firebaseId) {
        log.warn("About to delete user {}", firebaseId);
        try {
            FirebaseAuth.getInstance().deleteUser(firebaseId);
            log.trace("Successfully delete user {}", firebaseId);
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
        }
    }

    @Override
    public void setActiveAccount(String firebaseId, boolean isDisabled) {
        try {
            FirebaseAuth.getInstance().updateUser(new UserRecord.UpdateRequest(firebaseId).setDisabled(isDisabled));
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
        }
    }

    @Override
    public List<UserRecord> findAll() {
        List<UserRecord> users = new ArrayList<>(1000);
        boolean hasNext = true;
        String token = null;
        try {
            do {
                var listPage = FirebaseAuth.getInstance().listUsers(token);
                users.addAll(listPage.streamAll().toList());
                hasNext = listPage.hasNextPage();
                token = listPage.getNextPageToken();
            } while (hasNext);
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
        }
        return users;
    }

    @Override
    public String generateVerifyLink(String email) {
        try {
            var message = FirebaseAuth.getInstance().generateEmailVerificationLink(email);
            mailService.sendMail(MailRequest.builder()
                    .mail(email)
                    .subject("Xác thực email FusionTech")
                    .body("Truy cập đường link sau để xác thực email của bạn: \n\n" + message)
                    .build());
            return "URL to verify: " + message;
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
        }
        return "Sent successfully";
    }


    @Override
    public void updateRole(String firebaseUid, @NotNull List<Roles> roles) {
        var list = new ArrayList<>(roles.stream().map(Enum::name).toList());
        if (!list.contains(Roles.USER.name()))
            list.add(0, Roles.USER.name());
        try {
            auth().setCustomUserClaims(firebaseUid, Map.of(AuthoritiesConstant.ROLE_NAME, list));
            log.info("{}", auth().getUser(firebaseUid).getCustomClaims().get(AuthoritiesConstant.ROLE_NAME));
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
        }
    }

    @Override
    public List<String> getUserRoles(String firebaseUid) {
        try {
            var user = FirebaseAuth.getInstance().getUser(firebaseUid);
            var claims = user.getCustomClaims().get(AuthoritiesConstant.ROLE_NAME);
            return (List<String>) claims;
        } catch (FirebaseAuthException e) {
            log.error(e.getMessage());
//            handleFirebaseAuthException(e);
        }
        return null;
    }

//    @Override
//    public void updateVerifyEmail(Boolean verified) {
//        try {
//            var user = auth()
//        }
//    }

    @Override
    public Boolean verifyEmail(String email) {
        try {
            var user = auth().getUserByEmail(email);
            var dbUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
            if (user.isEmailVerified()) {
                dbUser.setIsVerified(true);
                userRepository.save(dbUser);
                return dbUser.getIsVerified();
            } else {
                throw new InvalidRequestException("INVALID: " + email);
            }
        } catch (FirebaseAuthException e) {
            handleFirebaseAuthException(e);
        }
        return false;
    }

    private Map<String, Object> getInitialClaims(Long id, @NonNull Roles...roles) {
        var list = Arrays.stream(roles).map(Enum::name).toList();
        return Map.of(AuthoritiesConstant.ROLE_NAME, list, "id", id);
    }

    private void handleFirebaseAuthException(FirebaseAuthException ex) throws RuntimeException {
        var authCode = ex.getAuthErrorCode() != null ? ex.getAuthErrorCode().name() : "";
        switch (ex.getErrorCode()) {
            case ALREADY_EXISTS -> throw new RecordExistsException(authCode);
            case NOT_FOUND -> throw new RecordNotFoundException(authCode);
            case INVALID_ARGUMENT -> throw new InvalidRequestException(authCode);
        }
        switch(ex.getAuthErrorCode()){
            case USER_NOT_FOUND, EMAIL_NOT_FOUND -> throw new RecordNotFoundException(authCode);
            case EMAIL_ALREADY_EXISTS, UID_ALREADY_EXISTS -> throw new RecordExistsException(authCode);
            case INVALID_ID_TOKEN -> throw new InvalidRequestException(authCode);
            case EXPIRED_ID_TOKEN, USER_DISABLED -> throw new RuntimeException(authCode);
            case UNAUTHORIZED_CONTINUE_URL -> throw new UnauthorizedException();
        }
        throw new RuntimeException(ex);
    }
}
