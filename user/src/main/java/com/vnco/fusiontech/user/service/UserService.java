package com.vnco.fusiontech.user.service;

import com.vnco.fusiontech.user.entity.ShippingAddress;
import com.vnco.fusiontech.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface UserService {
    ResponseEntity<User> createUser(@RequestBody User user);
    ResponseEntity<User> findUserById(@PathVariable UUID id);
    void updateDefaultShippingAddress(ShippingAddress address);
}
