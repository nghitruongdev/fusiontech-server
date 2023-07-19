package com.vnco.fusiontech.security.service.impl;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.*;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.vnco.fusiontech.common.constant.AuthoritiesConstant;
import com.vnco.fusiontech.common.exception.RecordExistsException;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.common.web.request.CreateUserRecord;
import com.vnco.fusiontech.common.web.request.RegisterUserWithEmailRequest;
import com.vnco.fusiontech.common.web.request.UpdateUserRequest;
import com.vnco.fusiontech.security.mapper.FirebaseUserMapper;
import com.vnco.fusiontech.security.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {
    private final PublicUserService userService;
    private final FirebaseUserMapper firebaseMapper;

    @Override
    public String registerWithGoogle(String firebaseId) {
        try {
            userService.existsByFirebaseId(firebaseId);
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(firebaseId);
            CreateUserRecord createUserRequest = firebaseMapper.toCreateUserDatabaseRecord(userRecord);
            var id = userService.register(createUserRequest);
            FirebaseAuth.getInstance().setCustomUserClaims(userRecord.getUid(), getInitialClaims(id));
            return FirebaseAuth.getInstance().createCustomToken(userRecord.getUid(), getInitialClaims(id));
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String registerUserWithEmail(RegisterUserWithEmailRequest request) {
        try {
            var user = firebaseMapper.toFirebaseCreateRequest(request);
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(user);
            CreateUserRecord createUserRecord = firebaseMapper.toCreateUserDatabaseRecord(userRecord, request);

            var id = userService.register(createUserRecord);
            FirebaseAuth.getInstance().setCustomUserClaims(userRecord.getUid(), getInitialClaims(id));
            return FirebaseAuth.getInstance().createCustomToken(userRecord.getUid(), getInitialClaims(id));
        } catch (FirebaseAuthException | IllegalArgumentException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    @Override
    public void updateUser(UpdateUserRequest request, Long userId) {
        // todo: if user not exists, what would happen
        String firebaseUid = userService.getFirebaseUid(userId).orElseThrow(RecordNotFoundException::new);
        try {
            UserRecord.UpdateRequest user = buildUpdateRequest(request, firebaseUid);
            FirebaseAuth.getInstance().updateUser(user);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e + "invalid information");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage() + "Invalid information");
        }
        userService.updateUser(request, userId);
    }

    @Override
    @SneakyThrows
    public void deleteUser(String firebaseId) {
        log.warn("About to delete user {}", firebaseId);
        FirebaseAuth.getInstance().deleteUser(firebaseId);
        log.info("Successfully delete user {}", firebaseId);
    }

    @Override
    public void updatePassword(Long userId, String password) {
        String firebaseUid = userService.getFirebaseUid(userId).orElseThrow(RecordNotFoundException::new);
        UserRecord.UpdateRequest user = new UserRecord.UpdateRequest(firebaseUid);
        try {
            FirebaseAuth.getInstance().updateUser(user);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
    }

    // todo: check lại user password
    private UserRecord.UpdateRequest buildUpdateRequest(UpdateUserRequest request, String uid) {
        UserRecord.UpdateRequest updateUser = new UserRecord.UpdateRequest(uid);
        if (request.firstName() != null || request.lastName() != null)
            updateUser.setDisplayName(request.fullName());
        if (request.email() != null) {
            updateUser.setEmail(request.email());
            updateUser.setEmailVerified(true);
        }
        if (request.password() != null)
            updateUser.setPassword(request.password());
        if (request.phoneNumber() != null)
            updateUser.setPhoneNumber(request.phoneNumber());
        if (request.photoUrl() != null)
            updateUser.setPhotoUrl(request.photoUrl());

        return updateUser;
    }

    private Map<String, Object> getInitialClaims(Long id) {
        return Map.of(AuthoritiesConstant.ROLE_NAME, List.of(AuthoritiesConstant.USER),
                "userId", id);
    }

}
