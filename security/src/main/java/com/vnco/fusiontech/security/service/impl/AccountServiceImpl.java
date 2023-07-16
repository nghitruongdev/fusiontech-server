package com.vnco.fusiontech.security.service.impl;

import com.google.cloud.storage.Acl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.common.web.request.CreateUserRequest;
import com.vnco.fusiontech.common.web.request.RegisterUser;
import com.vnco.fusiontech.common.web.request.UserUpdateRequest;
import com.vnco.fusiontech.security.service.AccountService;
import com.vnco.fusiontech.security.service.FirebaseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {


    private final PublicUserService userService;

    @Override
    public void googleRegister(String token) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        String uid = decodedToken.getUid();
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        CreateUserRequest createUserRequest = createNewUserRequest(userRecord);
        userService.register(createUserRequest);
    }

    @Override
    public String userRegister(RegisterUser request) throws FirebaseAuthException {
        String customToken = null;
        if (!userService.isUserExisted(request)) {
            UserRecord.CreateRequest user = createUserRequest(request);
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(user);
            Map<String, Object> information = prepareUserInfo(userRecord);
            CreateUserRequest createUserRequest = createNewUserRequest(userRecord);
            userService.register(createUserRequest);
            customToken = FirebaseAuth.getInstance().createCustomToken(userRecord.getUid(), information);
        } else log.error("User already exists ! : {}", request.email());
        return customToken;
    }

    @SneakyThrows
    @Override
    public void updateUser(UserUpdateRequest request, Long userId) {
        String firebaseUid = userService.getFirebaseUid(userId);
        UserRecord.UpdateRequest user = buildUpdateRequest(request, firebaseUid);
        UserRecord userRecord = FirebaseAuth.getInstance().updateUser(user);
        userService.updateUser(request, userId);
    }

    @Override
    public void deleteUser(String firebaseId) throws FirebaseAuthException {
        log.warn("About to delete user {}", firebaseId);
        FirebaseAuth.getInstance().deleteUser(firebaseId);
        log.info("Successfully delete user {}", firebaseId);
    }

    private UserRecord.UpdateRequest buildUpdateRequest(UserUpdateRequest request, String uid) {
        UserRecord.UpdateRequest updateUser = new UserRecord.UpdateRequest(uid);
        if (request.firstName() != null || request.lastName() != null)
            updateUser.setDisplayName(userService.composeFullName(request));
        if (request.email() != null) {
            updateUser.setEmail(request.email());
            updateUser.setEmailVerified(true);
        }
        if (request.password() != null)
            updateUser.setPassword(request.password());
        if (request.phoneNumber() != null)
            updateUser.setPhoneNumber(userService.convertToE164Format(request.phoneNumber()));
        if (request.photoUrl() != null)
            updateUser.setPhotoUrl(request.photoUrl());

        return updateUser;
    }
    private CreateUserRequest createNewUserRequest(UserRecord userRecord) {
        return new CreateUserRequest(
                userRecord.getUid(),
                userRecord.getDisplayName(),
                userRecord.getEmail(),
                userRecord.getPhoneNumber(),
                null
        );
    }
    private Map<String, Object> prepareUserInfo(UserRecord userRecord) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", userRecord.getDisplayName());
        claims.put("email", userRecord.getEmail());
        claims.put("phoneNumber", userRecord.getPhoneNumber());
        claims.put("photoUrl", userRecord.getPhotoUrl());
        return claims;
    }
    private UserRecord.CreateRequest createUserRequest(RegisterUser request) {
        return new UserRecord.CreateRequest()
                .setDisplayName(request.firstName() + " " + request.lastName())
                .setEmail(request.email())
                .setEmailVerified(true) // can create a method verify email here but don't have enough time
                .setDisabled(false)
                .setPassword(request.password())
                .setPhoneNumber(userService.convertToE164Format(request.phone()));
    }
}