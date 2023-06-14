package com.vnco.fusiontech.user.service;

import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.user.entity.ShippingAddress;
import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    @Override
    public ResponseEntity<User> createUser(User user) {
        return null;
    }

    @Override
    public ResponseEntity<User> findUserById(UUID id) {
        return null;
    }
    
    @Override
    public void updateDefaultShippingAddress(ShippingAddress address) {
        var userOptional           = repository.findById(address.getUser().getId());
        if(userOptional.isEmpty()){
            throw new RecordNotFoundException("No user was found with given id");
        }
        var user = userOptional.get();
        var defaultAddress = user.getDefaultAddress();
        if (defaultAddress == null || address.isDefault()) {
            user.setDefaultAddress(address);
        }
    }
}
