package com.vnco.fusiontech.order.entity.listener;

import com.vnco.fusiontech.order.entity.Order;
import com.vnco.fusiontech.order.entity.OrderStatus;
import com.vnco.fusiontech.order.entity.PaymentStatus;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
public class OrderListener {
    private Order order;
    
    @PrePersist
    public void prePersist(Order o) {
        log.debug("Pre persist order: {{}}", o);
        if(o.getStatus() == null){
            o.setStatus(OrderStatus.PLACED);
        }
        if(o.getPayment().getStatus() == null){
        o.getPayment().setStatus(PaymentStatus.UNPAID);
        }
        if(PaymentStatus.PAID == o.getPayment().getStatus()){
            o.getPayment().setPaidAt(Instant.now());
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
        this.order = o;
    }
    
    @PostRemove
    public void postRemove(Object o) {
        log.debug("Post remove order: {{}}", o);
    
    }
    
    @PostUpdate
    public void postUpdate(Order o) {
    
    }
    
    @PostPersist
    public void postPersist(Order o) {
        log.debug("Post persist order: {{}}", o);
    }
}
