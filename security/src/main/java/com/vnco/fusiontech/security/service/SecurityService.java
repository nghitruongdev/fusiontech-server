package com.vnco.fusiontech.security.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface SecurityService {
    
    List<GrantedAuthority> getAuthorities(Map<String, Object> claims);
    
}
