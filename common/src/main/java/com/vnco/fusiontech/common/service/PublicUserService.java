package com.vnco.fusiontech.common.service;



import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.vnco.fusiontech.common.web.request.CreateUserRequest;
import com.vnco.fusiontech.common.web.request.RegisterUser;
import com.vnco.fusiontech.common.web.request.UserUpdateRequest;

public interface PublicUserService {
    boolean existsById(Long id);
    boolean hasShippingAddress(Long userId, Long addressId);
    boolean isUserExisted(RegisterUser registerUser);
    String getFirebaseUid(Long userId);
    void register(CreateUserRequest request);
    void updateUser(UserUpdateRequest request, Long userId);

    String convertToE164Format(String phoneNumber);
    String composeFullName(UserUpdateRequest request);

}
