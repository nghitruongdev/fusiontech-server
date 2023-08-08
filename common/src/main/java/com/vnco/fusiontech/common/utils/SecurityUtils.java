package com.vnco.fusiontech.common.utils;

import com.vnco.fusiontech.common.constant.AuthoritiesConstant;
import com.vnco.fusiontech.common.entity.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Utility class to work with Spring Security
 */
@Slf4j
public class SecurityUtils {
    /**
     * Get username of current user
     *
     * @return username
     */
    public static Optional<AppUser> getCurrentUserLogin() {
        var context = getContext();
        log.debug("Authentication: {{}}", context.getAuthentication());
        return Optional.ofNullable(context.getAuthentication())
                       .map(Authentication::getPrincipal)
                       .filter(principal -> principal instanceof AppUser)
                       .map(principal -> (AppUser) principal);
    }
    
    
    /**
     * Get the JWT of the current user from credentials of authentication object
     *
     * @return JWT of current user
     */
    public static Optional<String> getCurrentUserJWTToken() {
        var context = getContext();
        return Optional
                       .ofNullable(context.getAuthentication())
                       .filter(auth -> auth.getCredentials() instanceof String)
                       .map(auth -> (String) auth.getCredentials());
    }
    
    public static void setAuthentication(Authentication authentication) {
        getContext().setAuthentication(authentication);
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        var auth = getContext().getAuthentication();
        return auth != null && getAuthorities(auth).noneMatch(AuthoritiesConstant.ANONYMOUS::equals);
    }
    
    /**
     * Checks if the current user has any of the authorities.
     *
     * @param authorities the authorities to check.
     * @return true if the current user has any of the authorities given, false otherwise.
     */
    public static boolean hasAnyOfAuthorities(String... authorities) {
        var auth = getContext().getAuthentication();
        return auth != null && getAuthorities(auth).anyMatch(
                authority -> Arrays.asList(authorities).contains(authority));
    }
    
    /**
     * Checks if the current user has none of the authorities.
     *
     * @param authorities the authorities to check.
     * @return true if the current user has none of the authorities, false otherwise.
     */
    public static boolean hasNoneOfAuthorities(String... authorities) {
        return !hasAnyOfAuthorities(authorities);
    }
    
    /**
     * Checks if the current user has a specific authority.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    public static boolean hasThisAuthority(String authority) {
        return hasAnyOfAuthorities(authority);
    }
    
    private static Stream<String> getAuthorities(Authentication auth) {
        return auth.getAuthorities()
                   .stream()
                   .map(GrantedAuthority::getAuthority);
    }
    
    private static SecurityContext getContext() {
        return SecurityContextHolder.getContext();
    }
    
    private static String extractPrincipal(Authentication auth) {
        if (auth == null)
            return null;
        
        if (auth.getPrincipal() instanceof UserDetails securityUser)
            return securityUser.getUsername();
        
        if (auth.getPrincipal() instanceof String username)
            return username;
        
        return null;
    }
    
}
