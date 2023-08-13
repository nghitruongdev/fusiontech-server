package com.vnco.fusiontech.product.event;


import com.vnco.fusiontech.common.entity.AppUser;
import com.vnco.fusiontech.common.exception.UnauthorizedException;
import com.vnco.fusiontech.common.utils.SecurityUtils;
import com.vnco.fusiontech.product.entity.VariantInventoryDetail;
import jakarta.persistence.*;

public class InventoryDetailListener {
    private AppUser auditUser;
    
    @PrePersist
    public void prePersist(Object o) {
    
    }
    
    @PreUpdate
    public void preUpdate(Object o) {
//        getAuditUser();
    }
    
    @PreRemove
    public void preRemove(Object o) {
//        getAuditUser();
    }
    
    @PostLoad
    public void postLoad(Object o) {
    
    }
    
    @PostRemove
    public void postRemove(VariantInventoryDetail o) {
//        o.getInventory().setLastModifiedBy(auditUser);
    }
    
    @PostUpdate
    public void postUpdate(VariantInventoryDetail o) {
//        o.getInventory().setLastModifiedBy(auditUser);
    }
    
    @PostPersist
    public void postPersist(Object o) {
    
    }
    
    private void getAuditUser() {
        auditUser = SecurityUtils.getCurrentUserLogin().orElseThrow(UnauthorizedException::new);
    }
}
