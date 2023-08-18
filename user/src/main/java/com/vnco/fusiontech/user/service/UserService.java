package com.vnco.fusiontech.user.service;

import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.user.entity.ShippingAddress;
import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.web.rest.request.UserRequest;

public interface UserService extends PublicUserService {
    void updateDefaultShippingAddress(Long userId, Long addressId);

    String registerWithEmail(UserRequest request);

    String registerWithGoogle(String firebaseId);

    Long createUser(UserRequest request);

    void updateUser(UserRequest request, Long userId);

    void setActiveUser(Long id, boolean isDisabled);
    // todo: remove favorite product when deleting user
    User updateUserForm(String uid, User user);

    ShippingAddress createShippingAddress(Long uid, ShippingAddress shippingAddress);
}
