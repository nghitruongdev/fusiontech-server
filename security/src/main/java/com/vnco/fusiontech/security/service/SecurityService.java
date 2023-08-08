package com.vnco.fusiontech.security.service;

import com.google.firebase.auth.FirebaseToken;
import com.vnco.fusiontech.common.entity.AppUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface SecurityService {
    
    List<GrantedAuthority> getAuthorities(Map<String, Object> claims);
    
    AppUser getCurrentUser(FirebaseToken token);
    
}
