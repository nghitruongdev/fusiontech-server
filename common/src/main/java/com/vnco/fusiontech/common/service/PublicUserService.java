package com.vnco.fusiontech.common.service;

import java.util.Optional;

import com.vnco.fusiontech.common.web.request.CreateUserRecord;
import com.vnco.fusiontech.common.web.request.UpdateUserRequest;

public interface PublicUserService {

    boolean existsById(Long id);

    boolean hasShippingAddress(Long userId, Long addressId);

    boolean isUserExists(String email);

    Optional<String> getFirebaseUid(Long userId);

    Long register(CreateUserRecord record);

    void updateUser(UpdateUserRequest request, Long userId);

    boolean existsByFirebaseId(String firebaseId);

}
