package com.vnco.fusiontech.user.web.rest.controller;

import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.repository.UserRepository;
import com.vnco.fusiontech.user.service.UserService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RepositoryRestController
public class UserRestController {

    private final UserService service;

    @Autowired
    private UserRepository userRepository;

    // get all users
    // @GetMapping("/users")
    // public List<User> getUsers() {
    // return userRepository.findAll();
    // }

    // create user
    // TODO: can't add password
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // get user by id
    // @GetMapping("/users/{id}")
    // public ResponseEntity<User> getUserById(@PathVariable UUID id) {
    // Optional<User> user = userRepository.findById(id);
    // if (user.isPresent()) return new ResponseEntity<>(user.get(), HttpStatus.OK);
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }

    // update user
    // TODO: can't update user
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User userToUpdated = user.get();
            User savedUser = userRepository.save(userToUpdated);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/users/{id}/defaultAddress/{aid}")
    public ResponseEntity<Void> updateDefaultAddress(@PathVariable("id") UUID userId,
            @PathVariable("aid") Long addressId) {
        service.updateDefaultShippingAddress(userId, addressId);
        log.warn("Done updating shipping address");
        return ResponseEntity.ok().build();
    }
}
