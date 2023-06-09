package com.vnco.fusiontech.auth.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class DecentralizedFunctionService {
    @PreAuthorize("hasRole('ADMIN')")
    public void abc() {

    }
}
