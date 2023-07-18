package com.vnco.fusiontech.user.web.rest.controller;

import com.vnco.fusiontech.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RepositoryRestController
public class UserRestController {

    private final UserService service;

    @PatchMapping("/users/{id}/defaultAddress/{aid}")
    public ResponseEntity<Void> updateDefaultAddress(@PathVariable("id") Long userId,
            @PathVariable("aid") Long addressId) {
        service.updateDefaultShippingAddress(userId, addressId);
        log.warn("Done updating shipping address");
        return ResponseEntity.ok().build();
    }
}
