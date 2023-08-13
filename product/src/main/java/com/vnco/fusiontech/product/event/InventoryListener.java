package com.vnco.fusiontech.product.event;

import com.vnco.fusiontech.common.exception.UnauthorizedException;
import com.vnco.fusiontech.common.utils.SecurityUtils;
import jakarta.persistence.*;

public class InventoryListener {
    @PrePersist
    public void prePersist(Object o) {
        SecurityUtils.getCurrentUserLogin().orElseThrow(UnauthorizedException::new);
    }
    
    @PreUpdate
    public void preUpdate(Object o) {
    
    }
    
    @PreRemove
    public void preRemove(Object o) {
    
    }
    
    @PostLoad
    public void postLoad(Object o) {
    
    }
    
    @PostRemove
    public void postRemove(Object o) {
    
    }
    
    @PostUpdate
    public void postUpdate(Object o) {
    
    }
    
    @PostPersist
    public void postPersist(Object o) {
    
    }
}
