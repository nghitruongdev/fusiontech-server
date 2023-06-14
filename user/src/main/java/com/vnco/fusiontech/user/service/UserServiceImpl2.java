package com.vnco.fusiontech.user.service;

import com.vnco.fusiontech.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public ResponseEntity<User> createUser(User user) {
        return null;
    }

    @Override
    public ResponseEntity<User> findUserById(UUID id) {
        return null;
    }
}
