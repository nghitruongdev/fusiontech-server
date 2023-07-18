package com.vnco.fusiontech.security.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.vnco.fusiontech.common.web.request.RegisterUser;
import com.vnco.fusiontech.common.web.request.UserUpdateRequest;

public interface AccountService {
    void googleRegister(String token) throws FirebaseAuthException;
    String userRegister(RegisterUser request) throws FirebaseAuthException;
    void updateUser(UserUpdateRequest token, Long userId);
    void deleteUser(String userId) throws FirebaseAuthException;
}
