package com.vnco.fusiontech.security.service.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.common.web.request.UserUpdateRequest;
import com.vnco.fusiontech.security.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final PublicUserService userService;

    @Override
    public void register(FirebaseToken decodedToken) throws FirebaseAuthException {
        log.info("registration starting...");
        userService.register(decodedToken);
    }

    @Override
    public void updateUser(UserUpdateRequest token, FirebaseToken decodedToken) throws FirebaseAuthException {
        userService.updateUser(token, decodedToken);
    }
}