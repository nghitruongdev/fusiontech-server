package com.vnco.fusiontech.user.service.impl;

import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.common.exception.RecordExistsException;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.common.web.request.CreateUserRecord;
import com.vnco.fusiontech.common.web.request.UpdateUserRequest;
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
    public boolean existsByFirebaseId(String firebaseId) {
        return repository.findByFirebaseUid(firebaseId).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasShippingAddress(Long userId, Long addressId) {
        return addressRepository.existsByIdAndUserId(addressId, userId);
    }

    @Override
    public Long register(CreateUserRecord record) {
        if (repository.existsByEmail(record.email()))
            throw new RecordExistsException("User already exists!");

        var user = User.builder()
                .firstName(record.firstName())
                .lastName(record.lastName())
                .firebaseUid(record.firebaseUid())
                .email(record.email())
                .phoneNumber(record.phoneNumber())
                .photoUrl(record.photoUrl())
                .build();
        return repository.save(user).getId();
    }

    // todo: update user with new field added
    @Override
    public void updateUser(UpdateUserRequest request, Long userId) {
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
    public boolean isUserExists(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Optional<String> getFirebaseUid(Long userId) {
        return repository.findById(userId).stream().map(User::getFirebaseUid).findFirst();
    }

    // todo: update user password
    private void updateUserProperties(UpdateUserRequest request, User user) {
        if (request.firstName() != null && !request.firstName().isEmpty())
            user.setFirstName(request.firstName());
        if (request.lastName() != null && !request.lastName().isEmpty())
            user.setLastName(request.lastName());
        if (request.email() != null)
            user.setEmail(request.email());
        if (request.phoneNumber() != null && !repository.existsByPhoneNumber(request.phoneNumber()))
            user.setPhoneNumber(request.phoneNumber());
        if (request.photoUrl() != null)
            user.setPhotoUrl(request.photoUrl());
    }
}
