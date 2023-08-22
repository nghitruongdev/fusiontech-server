package com.vnco.fusiontech.user.service;

import com.google.firebase.auth.UserRecord;
import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.entity.roles.Roles;
import com.vnco.fusiontech.user.web.rest.request.UserRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Validated
public interface AuthService {
    UserRecord registerWithEmailProvider(UserRequest request);

    UserRecord registerWithGoogleProvider(String firebaseId);

    String setInitialClaims(Long id, String firebaseId, Roles... roles);

    void updateProfile(User user, UserRequest request, String firebaseId);

    void updatePassword(String password, String firebaseId);

    void deleteAccount(String firebaseId);

    void setActiveAccount(@NotEmpty String firebaseId, boolean isDisabled);

    List<UserRecord> findAll();

    Boolean verifyEmail(String email);

    String generateVerifyLink(String email);

    void updateRole(String firebaseUid, @NotNull List<Roles> roles);

    List<String> getUserRoles(String firebaseUid);

//    void updateVerifyEmail(Boolean verified, String firebaseUid);

}
