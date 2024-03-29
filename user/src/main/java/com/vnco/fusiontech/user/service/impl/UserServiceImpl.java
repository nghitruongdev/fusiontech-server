package com.vnco.fusiontech.user.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.vnco.fusiontech.common.entity.AppUser;
import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.user.entity.ShippingAddress;
import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.entity.roles.Roles;
import com.vnco.fusiontech.user.repository.ShippingAddressRepository;
import com.vnco.fusiontech.user.repository.UserRepository;
import com.vnco.fusiontech.user.service.AuthService;
import com.vnco.fusiontech.user.service.UserService;
import com.vnco.fusiontech.user.web.rest.request.UserMapper;
import com.vnco.fusiontech.user.web.rest.request.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository            repository;
    private final ShippingAddressRepository addressRepository;
    private final UserMapper  mapper;
    private final AuthService authService;
    
    @Override
    public String registerWithEmail(UserRequest request) {
        var record = authService.registerWithEmailProvider(request);
        var user     = mapper.toUser(request).setFirebaseUid(record.getUid());
        try {
            repository.save(user);
        } catch (Exception e) {
            authService.deleteAccount(record.getUid());
            throw e;
        }
        return authService.setInitialClaims(user.getId(), record.getUid(), Roles.USER);
    }
    
    @Override
    public String registerWithGoogle(String firebaseId) {
        var record = authService.registerWithGoogleProvider(firebaseId);
        var user = repository.save(mapper.toUser(record));
        return authService.setInitialClaims(user.getId(), record.getUid(), Roles.USER);
    }
    
    @Override
    public Long createUser(UserRequest request) {
        var record = authService.registerWithEmailProvider(request);
        var user     = mapper.toUser(request).setFirebaseUid(record.getUid());
        user.setStaff(true);
        repository.save(user);
        authService.setInitialClaims(user.getId(), record.getUid());
        return user.getId();
    }
    
    @Override
    public void updateUser(UserRequest request, Long userId) {
        var user = repository.findById(userId).orElseThrow();
        authService.updateProfile(user,request, user.getFirebaseUid());
        mapper.partialUpdate(request, user);
    }
    
    @Override
    public void setActiveUser(Long userId, boolean isDisabled) {
        var user = repository.findById(userId).orElseThrow(RecordNotFoundException::new);
        authService.setActiveAccount(user.getFirebaseUid(), isDisabled);
        user.setDisabled(isDisabled);
    }
    
    @Override
    @Transactional (readOnly = true)
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
    
    @Override
    public Optional<AppUser> findByFirebaseId(String id) {
        return repository.findByFirebaseUid(id)
                         .map(user -> AppUser.builder()
                                              .id(user.getId())
                                              .displayName(user.getFullName())
                                              .build());
    }
    
    @Override
    @Transactional (readOnly = true)
    public boolean existsByFirebaseId(String firebaseId) {
        return repository.findByFirebaseUid(firebaseId).isPresent();
    }
    
    @Override
    @Transactional (readOnly = true)
    public boolean hasShippingAddress(Long userId, Long addressId) {
        return addressRepository.existsByIdAndUserId(addressId, userId);
    }
    
    @Override
    public boolean isUserExists(String email) {
        return repository.existsByEmail(email);
    }
    
    @Override
    public Optional<String> getFirebaseUid(Long userId) {
        return repository.findById(userId).stream().map(User::getFirebaseUid).findFirst();
    }
    
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
    public Long countUsers() {
        return repository.countAllUsers();
    }


    @Override
    public User updateUserForm(String uid, User user) {
        Optional<User> userOptional = repository.findByFirebaseUid(uid);
        if (userOptional.isPresent()) {
            User userStore = userOptional.get();
            userStore.setFirebaseUid(user.getFirebaseUid());
            userStore.setFirstName(user.getFullName());
            userStore.setEmail(user.getEmail());
            userStore.setPhoneNumber(user.getPhoneNumber());
            userStore.setDateOfBirth(user.getDateOfBirth());
            userStore.setGender(user.getGender());
            return repository.save(userStore);
        } else {
            throw new RecordNotFoundException("User not found with ID: " + uid);
        }
    }

    @Override
    public ShippingAddress createShippingAddress(Long uid, ShippingAddress shippingAddress) {
        Optional<User> userOptional = repository.findById(uid);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            ShippingAddress shippingAddressStore = new ShippingAddress();
            shippingAddressStore.setUser(user);
            shippingAddressStore.setName(shippingAddress.getName());
            shippingAddressStore.setPhone(shippingAddress.getPhone());
            shippingAddressStore.setProvince(shippingAddress.getProvince());
            shippingAddressStore.setDistrict(shippingAddress.getDistrict());
            shippingAddressStore.setWard(shippingAddress.getWard());
            shippingAddressStore.setAddress(shippingAddress.getAddress());
            return addressRepository.save(shippingAddressStore);
        } else {
            throw new RecordNotFoundException("User not found with ID: " + uid);
        }
    }
}
