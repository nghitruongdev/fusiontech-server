package com.vnco.fusiontech.user.entity;

import com.vnco.fusiontech.common.utils.BeanUtils;
import com.vnco.fusiontech.user.service.UserService;
import jakarta.persistence.*;

public class ShippingAddressListener {

    @PrePersist
    public void prePersist(ShippingAddress o) {

    }

    @PreUpdate
    public void preUpdate(ShippingAddress o) {
    }

    @PreRemove
    public void preRemove(ShippingAddress o) {

    }

    @PostLoad
    public void postLoad(ShippingAddress o) {

    }

    @PostRemove
    public void postRemove(ShippingAddress o) {

    }

    @PostUpdate
    public void postUpdate(ShippingAddress o) {
        updateDefault(o);
    }

    @PostPersist
    public void postPersist(ShippingAddress o) {
        updateDefault(o);
    }

    void updateDefault(ShippingAddress address) {
        var userService = BeanUtils.getBean(UserService.class);
        new Thread(()->{
            if (address.isDefault() || address.getUser().getDefaultAddress() == null) {
                userService.updateDefaultShippingAddress(address.getUser().getId(), address.getId());
            }
        }).start();
    }
}
