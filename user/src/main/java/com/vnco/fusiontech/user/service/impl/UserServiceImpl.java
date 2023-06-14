package com.vnco.fusiontech.user.service.impl;

import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.user.entity.ShippingAddress;
import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.repository.ShippingAddressRepository;
import com.vnco.fusiontech.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.vnco.fusiontech.user.service.UserService;
import com.vnco.fusiontech.user.web.request.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ShippingAddressRepository addressRepository;

    @Override
    public ResponseEntity<User> createUser(User user) {
        return null;
    }

    @Override
    public ResponseEntity<User> findUserById(UUID id) {
        return null;
    }

    @Override
    public void updateDefaultShippingAddress(UUID userId, Long addressId) {
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
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasShippingAddress(UUID userId, Long addressId) {
        return addressRepository.existsByIdAndUserId(addressId, userId);
    }
}
