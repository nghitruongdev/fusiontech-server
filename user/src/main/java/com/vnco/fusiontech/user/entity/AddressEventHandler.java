package com.vnco.fusiontech.user.entity;

import com.vnco.fusiontech.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@RepositoryEventHandler
@Component
public class AddressEventHandler {
    private final UserService userService;
    
    @HandleBeforeSave
    public void handleBeforeSave(ShippingAddress address){
        log.debug("Address before save: {}", address);
    }
    
    @HandleBeforeCreate
    public void handleBeforeCreate(ShippingAddress address){
        log.debug("Address before create: {}", address);
    }
    
    @HandleAfterCreate
    public void handleAfterCreate(ShippingAddress address){
        log.debug("Address after create: {}", address);
        if(address.isDefault()){
            userService.updateDefaultShippingAddress(address.getUser().getId(), address.getId());
        }
    }
    
    @HandleAfterSave
    public void handleAfterSave(ShippingAddress address){
        if(address.isDefault()){
            userService.updateDefaultShippingAddress(address.getUser().getId(), address.getId());
        }
    }
}
