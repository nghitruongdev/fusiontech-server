package com.vnco.fusiontech.common.service;

import com.vnco.fusiontech.common.entity.AppUser;

import java.util.Optional;

public interface PublicUserService {

    boolean existsById(Long id);

    Optional<AppUser> findByFirebaseId(String id);
    
    boolean hasShippingAddress(Long userId, Long addressId);

    boolean isUserExists(String email);

    Optional<String> getFirebaseUid(Long userId);

    boolean existsByFirebaseId(String firebaseId);

}
