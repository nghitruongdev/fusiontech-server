package com.vnco.fusiontech.security.filter;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.vnco.fusiontech.security.service.FirebaseService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Slf4j
public class FirebaseTokenFilter extends OncePerRequestFilter {

    final
    FirebaseService firebaseService;

    public FirebaseTokenFilter(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("Logging filter invoked...");
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = authHeader.substring(7);
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();
            List<GrantedAuthority> authorities = firebaseService.getAuthorities(
                    FirebaseAuth.getInstance().getUser(uid).getCustomClaims()
            );
            Authentication authentication = new UsernamePasswordAuthenticationToken(uid, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("User's authorities {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        } catch (FirebaseAuthException e) {
            log.error("Firebase authentication failed", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Firebase authentication failed");
        }
    }
}

