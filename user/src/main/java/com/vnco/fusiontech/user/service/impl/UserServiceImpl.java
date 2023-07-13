package com.vnco.fusiontech.user.service.impl;

import com.google.firebase.auth.*;
import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
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
    public void register(FirebaseToken decodedToken) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(decodedToken.getUid());
        User user = User.builder()
                .firebaseUid(userRecord.getUid())
                .name(userRecord.getDisplayName())
                .email(userRecord.getEmail())
                .phoneNumber(userRecord.getPhoneNumber())
                .photoUrl(userRecord.getPhotoUrl())
                .build();
        repository.save(user);
        log.info("save user completed ! : {}", user.getId());
    }

    @Override
    public void updateUser(UserUpdateRequest updateRequest, FirebaseToken decodedToken) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(decodedToken.getUid());
        User user = repository.findByFirebaseUid(userRecord.getUid());
        Optional<User> userOptional = Optional.ofNullable(user);
        if (userOptional.isPresent()) {

            updateRequest.name().ifPresent(user::setName);
            updateRequest.email().ifPresent(user::setEmail);
            updateRequest.phoneNumber().ifPresent(user::setPhoneNumber);
            updateRequest.photoUrl().ifPresent(user::setPhoneNumber);
            repository.save(user);
        } else {
            throw new RuntimeException("user not found!");
        }



    }
}
