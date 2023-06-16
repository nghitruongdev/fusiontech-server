package com.vnco.fusiontech.common.service;



import com.vnco.fusiontech.common.web.request.RegisterRequest;

import java.util.Optional;
import java.util.UUID;

public interface PublicUserService {
    boolean existsById(UUID id);
    boolean hasShippingAddress(UUID userId, Long addressId);
    UUID register(RegisterRequest registerRequest);

}
