package com.vnco.fusiontech.user.service.impl;

import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.service.UserService;
import com.vnco.fusiontech.user.web.request.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public ResponseEntity<User> createUser(User user) {
        return null;
    }

    @Override
    public ResponseEntity<User> findUserById(UUID id) {
        return null;
    }
}

