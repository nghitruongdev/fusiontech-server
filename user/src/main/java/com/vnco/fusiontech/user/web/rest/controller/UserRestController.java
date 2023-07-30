package com.vnco.fusiontech.user.web.rest.controller;

import com.vnco.fusiontech.user.service.UserService;
import com.vnco.fusiontech.user.web.rest.request.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin ("*")
@Slf4j
@RequiredArgsConstructor
@RepositoryRestController
public class UserRestController {
    
    private final UserService service;
    
    @PostMapping ("/users")
    @Validated(UserRequest.OnCreate.class)
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(request));
    }
    
    
    @PatchMapping ("/users/{id}/defaultAddress/{aid}")
    public ResponseEntity<Void> updateDefaultAddress(@PathVariable ("id") Long userId,
                                                     @PathVariable ("aid") Long addressId
    ) {
        service.updateDefaultShippingAddress(userId, addressId);
        log.warn("Done updating shipping address");
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/users/{id}/status")
    public ResponseEntity<Void> setActiveUser(@PathVariable Long id,
                                              @RequestParam("isDisabled") boolean isDisabled){
        service.setActiveUser(id, isDisabled);
        return ResponseEntity.ok().build();
    }
}
