package com.vnco.fusiontech.user.service;

import com.google.firebase.auth.UserRecord;
import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.web.rest.request.UserRequest;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface AuthService {
    UserRecord registerWithEmailProvider(UserRequest request);
    
    UserRecord registerWithGoogleProvider(String firebaseId);
    
    String setInitialClaims(Long id, String firebaseId);
    
    void updateProfile(User user, UserRequest request, String firebaseId);
    
    void updatePassword(String password, String firebaseId);
    
    void deleteAccount(String firebaseId);
    
    void setActiveAccount(@NotEmpty String firebaseId, boolean isDisabled);
    
    List<UserRecord> findAll();

    String verifyEmail(String email);
}
