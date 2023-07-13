package com.vnco.fusiontech.security.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.vnco.fusiontech.common.web.request.UserUpdateRequest;

public interface AccountService {
    void register(FirebaseToken request) throws FirebaseAuthException;
    void updateUser(UserUpdateRequest token, FirebaseToken decodedToken) throws FirebaseAuthException;
}
