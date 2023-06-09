package com.vnco.fusiontech.user.service;

import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.web.request.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface UserService {
    ResponseEntity<User> createUser(@RequestBody User user);
    ResponseEntity<User> findUserById(@PathVariable UUID id);

}
