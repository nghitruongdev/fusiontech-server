package com.vnco.fusiontech.order.listener;

import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.order.entity.Order;
import com.vnco.fusiontech.order.entity.Voucher;
import jakarta.persistence.*;
import jakarta.validation.Valid;

public class VoucherListener {
    @PrePersist
    public void prePersist(Voucher o) {

    }

    @PreUpdate
    public void preUpdate(@Valid Voucher o) {
        if (o.getExpirationDate() != null && o.getStartDate() != null)
            if (o.getExpirationDate().isBefore(o.getStartDate()))
                throw new InvalidRequestException("Thời gian kết thúc phải sau ngày bắt đầu");
    }

    @PreRemove
    public void preRemove(Voucher o) {

    }

//    @PostLoad
//    public void postLoad(Object o) {
//
//    }
//
//    @PostRemove
//    public void postRemove(Object o) {
//
//    }
//
//    @PostUpdate
//    public void postUpdate(Object o) {
//
//    }
//
//    @PostPersist
//    public void postPersist(Object o) {
//
//    }
}
