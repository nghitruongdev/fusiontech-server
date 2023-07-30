package com.vnco.fusiontech.common.service;

import java.util.Optional;

public interface PublicUserService {

    boolean existsById(Long id);

    boolean hasShippingAddress(Long userId, Long addressId);

    boolean isUserExists(String email);

    Optional<String> getFirebaseUid(Long userId);

    boolean existsByFirebaseId(String firebaseId);

}
