package com.vnco.fusiontech.user.service;

import com.vnco.fusiontech.common.service.PublicUserService;

public interface UserService extends PublicUserService {
    void updateDefaultShippingAddress(Long userId, Long addressId);
    // todo: remove favorite product when deleting user
}
