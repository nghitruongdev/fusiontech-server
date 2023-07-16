package com.vnco.fusiontech.user.service.impl;

import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.common.web.request.CreateUserRequest;
import com.vnco.fusiontech.common.web.request.RegisterUser;
import com.vnco.fusiontech.common.web.request.UserUpdateRequest;
import com.vnco.fusiontech.user.entity.ShippingAddress;
import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.repository.ShippingAddressRepository;
import com.vnco.fusiontech.user.repository.UserRepository;
import com.vnco.fusiontech.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ShippingAddressRepository addressRepository;


    @Override
    public void updateDefaultShippingAddress(Long userId, Long addressId) {
        var userOptional = repository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RecordNotFoundException("No user was found with given id");
        }
        var user = userOptional.get();
        if (!hasShippingAddress(userId, addressId)) {
            throw new InvalidRequestException("Address was not found with user");
        }
        user.setDefaultAddress(new ShippingAddress(addressId));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasShippingAddress(Long userId, Long addressId) {
        return addressRepository.existsByIdAndUserId(addressId, userId);
    }

    @Override
    public void register(CreateUserRequest request) {
        if (!repository.existsByEmail(request.email())) {
            repository.save(User.builder()
                    .firebaseUid(request.firebaseUid())
                    .name(request.name())
                    .email(request.email())
                    .phoneNumber(request.phoneNumber())
                    .photoUrl(request.photoUrl())
                    .build());
            log.info("Successfully register new user: {}", request.firebaseUid());
        } else {
            log.error("User already exists! {}", request.email());
        }
    }

    @Override
    public void updateUser(UserUpdateRequest request, Long userId) {
        Optional<User> userOptional = repository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            updateUserProperties(request, user);
            repository.save(user);
            log.info("Successfully update user: {}", request);
        } else {
            log.error("User not exists! {}", userId);
        }
    }

    @Override
    public boolean isUserExisted(RegisterUser registerUser) {
        return repository.existsByEmail(registerUser.email());
    }

    @Override
    public String getFirebaseUid(Long userId) {
        Optional<User> userOptional = repository.findById(userId);
        User user = null;
        if (userOptional.isPresent())
            user = userOptional.get();
        return user != null ? user.getFirebaseUid() : null;
    }

    @Override
    public String convertToE164Format(String phoneNumber) {
        String cleanNumber = phoneNumber.replaceAll("[^0-9]", ""); // remove non digits
        if (cleanNumber.startsWith("0")) {
            cleanNumber = "84" + cleanNumber.substring(1); // replace leading 0 with 84
        }
        return "+" + cleanNumber; // prepend +
    }

    @Override
    public String composeFullName(UserUpdateRequest request) {
        String firstName = (request.firstName() != null) ? request.firstName().trim() : "";
        String lastName = (request.lastName() != null) ? request.lastName().trim() : "";
        return (firstName + " " + lastName).trim();
    }

    private void updateUserProperties(UserUpdateRequest request, User user) {
        if ((request.firstName() != null && !request.firstName().isEmpty()) ||
            (request.lastName()) != null && !request.lastName().isEmpty())
            user.setName(composeFullName(request));
        if (request.email() != null)
            user.setEmail(request.email());
        if (request.phoneNumber() != null && !repository.existsByPhoneNumber(request.phoneNumber()))
            user.setPhoneNumber(convertToE164Format(request.phoneNumber()));
        if (request.photoUrl() != null)
            user.setPhotoUrl(request.photoUrl());
    }
}
