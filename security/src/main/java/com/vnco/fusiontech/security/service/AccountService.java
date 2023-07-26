package com.vnco.fusiontech.security.service;

import com.vnco.fusiontech.common.web.request.RegisterUserWithEmailRequest;
import com.vnco.fusiontech.common.web.request.UpdateUserRequest;

public interface AccountService {

    String registerWithGoogle(String firebaseId);

    String registerUserWithEmail(RegisterUserWithEmailRequest request);

    void updateUser(UpdateUserRequest token, Long userId);

    void deleteUser(String userId);

    void updatePassword(Long userId, String password);
}
