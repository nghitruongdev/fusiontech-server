package com.vnco.fusiontech.security.service.impl;

import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.common.web.request.RegisterRequest;
import com.vnco.fusiontech.security.service.RegisterService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final PublicUserService userService;


    @Override
    public UUID register(RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }
}
