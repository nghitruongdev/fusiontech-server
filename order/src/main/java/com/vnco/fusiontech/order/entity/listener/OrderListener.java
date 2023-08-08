package com.vnco.fusiontech.order.entity.listener;

import com.vnco.fusiontech.common.constant.OrderStatus;
import com.vnco.fusiontech.common.constant.PaymentStatus;
import com.vnco.fusiontech.common.service.PublicMailService;
import com.vnco.fusiontech.common.utils.BeanUtils;
import com.vnco.fusiontech.common.web.request.mail.OrderRequest;
import com.vnco.fusiontech.order.entity.Order;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class OrderListener {
    private Order loadedOrder;
    
    @PrePersist
    public void prePersist(Order o) {
        log.debug("Pre persist order: {{}}", o);
        if(o.getStatus() == null){
            o.setStatus(OrderStatus.PLACED);
        }
        if(o.getPayment().getStatus() == null){
        o.getPayment().setStatus(PaymentStatus.PENDING);
        }
        if(PaymentStatus.COMPLETED == o.getPayment().getStatus()){
            o.getPayment().setPaidAt(LocalDateTime.now());
        }
    }
    
    @PreUpdate
    public void preUpdate(Object o) {
        log.debug("Pre update order: {{}}", o);
        
    }
    
    @PreRemove
    public void preRemove(Object o) {
        log.debug("Pre remove order: {{}}", o);
    
    }
    
    @PostLoad
    public void postLoad(Order o) {
        log.debug("Post load order: {{}}", o);
        this.loadedOrder = o;
    }
    
    @PostRemove
    public void postRemove(Object o) {
        log.debug("Post remove order: {{}}", o);
    
    }
    
    @PostUpdate
    public void postUpdate(Order o) {
        if(loadedOrder.getStatus() != o.getStatus()){
            if(o.getEmail() != null){
                var request = OrderRequest.builder()
                            .mail(o.getEmail()).subject("Thông báo thay đổi tình trạng đơn hàng")
                            .body( """
                                                                          Đơn hàng đã được cập nhật trạng thái từ
                                                                           %s sang %s
                                                                           Xin chân thành cám ơn.
                                                                            """.formatted(loadedOrder.getStatus(), o.getStatus()))
                            .build();
                getMailService().sendMail(request);
            }
        }
    }
    
    @PostPersist
    public void postPersist(Order o) {
        log.debug("Post persist order: {{}}", o);
        if(o.getEmail() != null){
           var request =  OrderRequest.builder()
                        .mail(o.getEmail()).subject("Đặt hàng thành công")
                                                             .body( """
                                                                           Xin chào,
                                                                           Bạn đã đặt hàng thành công.
                                                                           %s
                                                                           Xin cám ơn ơn.
                                                                            """.formatted(
                                                                                    o.toString()))
                        .build();
            getMailService().sendMail(request);
                       
        }
    }
    
    private PublicMailService getMailService(){
        return BeanUtils.getBean(PublicMailService.class);
    }
}
