package com.vnco.fusiontech.security.service;

import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.common.web.request.RegisterRequest;

import java.util.UUID;

public interface RegisterService {
    UUID register(RegisterRequest registerRequest);
}
