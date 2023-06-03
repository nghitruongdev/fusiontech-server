package com.vnco.fusiontech.user.service;

import com.vnco.fusiontech.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public ResponseEntity<User> createUser(User user) {
        return null;
    }

    @Override
    public ResponseEntity<User> findUserById(Long id) {
        return null;
    }
}
