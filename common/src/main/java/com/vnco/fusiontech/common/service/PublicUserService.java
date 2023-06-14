package com.vnco.fusiontech.common.service;


import com.vnco.fusiontech.common.entity.AppUser;

import java.util.Optional;
import java.util.UUID;

public interface PublicUserService {
    boolean existsById(UUID id);
    Optional<AppUser> findById(UUID id);
}
