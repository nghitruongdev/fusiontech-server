package com.vnco.fusiontech.user.service.impl;

import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.common.web.request.RegisterRequest;
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
    private final UserRepository            repository;
    private final ShippingAddressRepository addressRepository;


    @Override
    public void updateDefaultShippingAddress(ShippingAddress address) {
        var userOptional = repository.findById(address.getUser().getId());
        if (userOptional.isEmpty()) {
            throw new RecordNotFoundException("No user was found with given id");
        }
        var user           = userOptional.get();
        var defaultAddress = user.getDefaultAddress();
        if (defaultAddress == null || address.isDefault()) {
            user.setDefaultAddress(address);
        }
    }
    
    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
    
    @Override
    public boolean hasShippingAddress(UUID userId, Long addressId) {
        return addressRepository.existsByIdAndUserId(addressId, userId);
    }

    @Override
    public UUID register(RegisterRequest registerRequest) {
        if (repository.existsByUsername(registerRequest.username())) {
            throw new RuntimeException("user already existed !");
        }
        User user = new User();
        if (repository.existsUsersByEmail(registerRequest.email())) {
            throw new RuntimeException("this email already existed !");
        }
        user.setUsername(registerRequest.username());
        user.setPasswordHash(registerRequest.password());
        user.setEmail(registerRequest.email());
        user.setPhone(registerRequest.phone());

        repository.save(user);
        return user.getId();
    }
}
