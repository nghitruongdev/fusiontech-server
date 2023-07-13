package com.vnco.fusiontech.user.service;

import com.google.firebase.auth.FirebaseAuth;
import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.user.entity.ShippingAddress;
import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.web.request.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface UserService extends PublicUserService {
    void updateDefaultShippingAddress(Long userId, Long addressId);

    //todo: remove favorite product when deleting user
}
