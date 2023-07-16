package com.vnco.fusiontech.security.service;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface FirebaseService {
    List<GrantedAuthority> getAuthorities(Map<String, Object> claims);

}
