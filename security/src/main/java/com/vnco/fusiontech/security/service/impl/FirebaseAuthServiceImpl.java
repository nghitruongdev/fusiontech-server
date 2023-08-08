package com.vnco.fusiontech.security.service.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.vnco.fusiontech.common.constant.AuthoritiesConstant;
import com.vnco.fusiontech.common.entity.AppUser;
import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.common.exception.RecordExistsException;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseAuthServiceImpl implements SecurityService {
    private final PublicUserService userService;
    
    @Override
    public List<GrantedAuthority> getAuthorities(Map<String, Object> claims) {
        var claim = claims.get(AuthoritiesConstant.ROLE_NAME);
        if (claim instanceof List<?> list) {
            return list.stream()
                       .map(String::valueOf)
                       .map(item -> AuthoritiesConstant.ROLE_PREFIX + item.toUpperCase())
                       .map(SimpleGrantedAuthority::new)
                       .map(item -> (GrantedAuthority) item)
                       .toList();
        }
        return List.of();
    }
    
    @Override
    public AppUser getCurrentUser(FirebaseToken token) {
        var idClaim     = token.getClaims().get("id");
        var authorities = getAuthorities(token.getClaims());
        
        if (idClaim != null) {
            try {
                var id = Long.parseLong(String.valueOf(idClaim));
                return AppUser.builder()
                              .id(id)
                              .displayName(token.getName())
                               .emailVerified(token.isEmailVerified())
                              .authorities(authorities)
                              .build();
            } catch (NumberFormatException e) {
                log.warn("ID Claim is not correct format.");
                return null;
            }
        }
        return userService.findByFirebaseId(token.getUid())
                          .stream()
                          .peek(user1 -> user1.setAuthorities(authorities)).findFirst().orElse(null);
    }
    
    
    private static List<String> convertObjectToList(Object obj) {
        List<String> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.stream((Object[]) obj)
                         .map(Object::toString)
                         .collect(Collectors.toList());
        } else if (obj instanceof Collection) {
            list = ((Collection<?>) obj).stream()
                                        .map(Object::toString).collect(Collectors.toList());
        }
        return list;
    }
    
    private void handleFirebaseAuthException(FirebaseAuthException ex){
        var authCode = ex.getAuthErrorCode().name();
        switch (ex.getErrorCode()){
            case ALREADY_EXISTS ->
                throw new RecordExistsException(authCode);
            case NOT_FOUND ->
                throw new RecordNotFoundException(authCode);
            case INVALID_ARGUMENT ->
                throw new InvalidRequestException(authCode);
        }
    }
    
    
}