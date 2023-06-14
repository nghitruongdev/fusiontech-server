package com.vnco.fusiontech.user.web.rest.controller;


import com.vnco.fusiontech.user.entity.Authority;
import com.vnco.fusiontech.user.entity.Role;
import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.service.AuthorityService;
import com.vnco.fusiontech.user.web.request.AuthorityCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/authorities")
@Slf4j
public class AuthorityRestController {

    final
    AuthorityService authorityService;

    public AuthorityRestController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping
    public List<Authority> getAuthorities() {
        return authorityService.getAllAuthorities();
    }

    @PostMapping
    public ResponseEntity<Authority> createAuthority(@RequestBody AuthorityCreateRequest authority) {
        log.debug("authority request: {}", authority);
        Authority newAuthority = authorityService.createAuthority(authority.userId(), authority.roleName());
        return new ResponseEntity<>(newAuthority, HttpStatus.CREATED);
    }

}


