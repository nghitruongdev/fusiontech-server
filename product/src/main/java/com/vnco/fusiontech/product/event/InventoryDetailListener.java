package com.vnco.fusiontech.product.event;


import com.vnco.fusiontech.common.entity.AppUser;
import com.vnco.fusiontech.common.exception.UnauthorizedException;
import com.vnco.fusiontech.common.utils.BeanUtils;
import com.vnco.fusiontech.common.utils.SecurityUtils;
import com.vnco.fusiontech.product.entity.VariantInventoryDetail;
import com.vnco.fusiontech.product.repository.VariantInventoryRepository;
import jakarta.persistence.*;
import org.hibernate.Hibernate;
import org.springframework.data.auditing.AuditingHandler;

public class InventoryDetailListener {
    
    @PrePersist
    public void prePersist(Object o) {
    
    }
    
    @PreUpdate
    public void preUpdate(VariantInventoryDetail o) {
    }
    
    @PreRemove
    public void preRemove(Object o) {
    }
    
    @PostLoad
    public void postLoad(VariantInventoryDetail o) {
    }
    
    @PostRemove
    public void postRemove(VariantInventoryDetail o) {
    }
    
    @PostUpdate
    public void postUpdate(VariantInventoryDetail o) {
        updateLastModified(o);
    }
    
    @PostPersist
    public void postPersist(Object o) {
    
    }
    
    private AppUser getAuditUser() {
        return SecurityUtils.getCurrentUserLogin().orElseThrow(UnauthorizedException::new);
    }
    
    private void updateLastModified(VariantInventoryDetail o) {
    }
}
