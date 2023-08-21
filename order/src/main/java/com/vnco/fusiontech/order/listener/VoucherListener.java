package com.vnco.fusiontech.order.listener;

import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.common.exception.NotAcceptedRequestException;
import com.vnco.fusiontech.order.entity.Voucher;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.Objects;

public class VoucherListener {
    
    private Byte loadedDiscount;
    
    @PostLoad
    public void postLoad(Voucher o) {
    loadedDiscount = o.getDiscount();
    }
    
    @PrePersist
    public void prePersist(Voucher o) {
        checkValidTime(o);
    }
    
    @PreUpdate
    public void preUpdate(Voucher o) {
        checkValidTime(o);
        if (o.getUsage() != 0 && !Objects.equals(loadedDiscount, o.getDiscount())) {
            throw new NotAcceptedRequestException("Không thể thay đổi  giảm giá, voucher đã được sử dụng");
        }
    }
    
    private void checkValidTime(Voucher o) {
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
