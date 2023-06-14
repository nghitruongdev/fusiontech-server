package com.vnco.fusiontech.common.service;



import java.util.Optional;
import java.util.UUID;

public interface PublicUserService {
    boolean existsById(UUID id);
    boolean hasShippingAddress(UUID userId, Long addressId);
}
