package com.vnco.fusiontech.common.service;



import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.vnco.fusiontech.common.web.request.RegisterRequest;
import com.vnco.fusiontech.common.web.request.UserUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public interface PublicUserService {
    boolean existsById(Long id);
    boolean hasShippingAddress(Long userId, Long addressId);
    void register(FirebaseToken decodedToken) throws FirebaseAuthException;
    void updateUser(UserUpdateRequest token, FirebaseToken decodedToken) throws FirebaseAuthException;

}
