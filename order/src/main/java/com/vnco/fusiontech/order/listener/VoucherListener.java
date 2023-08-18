package com.vnco.fusiontech.order.listener;

import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.common.utils.BeanUtils;
import com.vnco.fusiontech.order.entity.Voucher;
import com.vnco.fusiontech.order.repository.OrderRepository;
import com.vnco.fusiontech.order.repository.VoucherRepository;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

public class VoucherListener {

    @Autowired
    OrderRepository repository;

    @PrePersist
    public void prePersist(Voucher o) {
        if (o.getExpirationDate() != null && o.getStartDate() != null)
            if (o.getExpirationDate().isBefore(o.getStartDate()))
                throw new InvalidRequestException("Thời gian kết thúc phải sau ngày bắt đầu");
    }

    @PreUpdate
    public void preUpdate(@Valid Voucher o) {
        if (o.getExpirationDate() != null && o.getStartDate() != null)
            if (o.getExpirationDate().isBefore(o.getStartDate()))
                throw new InvalidRequestException("Thời gian kết thúc phải sau ngày bắt đầu");
    }

//    @PreRemove
//    public void preRemove(Voucher o) {
//       var repo =  BeanUtils.getBean(VoucherRepository.class);
//       var count = repo.countVoucherUsageByCode(o.getCode());
//       if (count != 0)
//            throw new InvalidRequestException("Voucher đã được sử dụng không thể xoá");
//    }

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
