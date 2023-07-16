package com.vnco.fusiontech.security.service.impl;

import com.vnco.fusiontech.common.constant.AuthoritiesConstant;
import com.vnco.fusiontech.security.service.FirebaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FirebaseServiceImpl implements FirebaseService {
    @Override
    public List<GrantedAuthority> getAuthorities(Map<String, Object> claims) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        var claimObject = claims.get(AuthoritiesConstant.ROLE_NAME);
//
//        if (claimObject != null) {
//            List<?> claim = convertObjectToList(claimObject);
//
//            for (String role : claim) {
//                authorities.add(new SimpleGrantedAuthority(AuthoritiesConstant.ROLE_PREFIX + role));
//            }
//        }
//        return authorities;
        var claim = claims.get(AuthoritiesConstant.ROLE_NAME);
        if (claim instanceof List<?> list) {
            return list.stream()
                    .map(String::valueOf)
                    .map(item -> AuthoritiesConstant.ROLE_PREFIX + item.toUpperCase())
                    .map(SimpleGrantedAuthority::new)
                    .map(item -> (GrantedAuthority)item)
                    .toList();
        }
        return List.of();
    }
    public static List<String> convertObjectToList(Object obj) {
        List<String> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.stream((Object[])obj)
                    .map(Object::toString)
                    .collect(Collectors.toList());
        } else if (obj instanceof Collection) {
            list = ((Collection<?>)obj).stream()
                    .map(Object::toString).collect(Collectors.toList());
        }
        return list;
    }
}
