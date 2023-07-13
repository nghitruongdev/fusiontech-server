package com.vnco.fusiontech.security.filter;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserRecord;
import com.vnco.fusiontech.common.constant.FirebaseConstant;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Slf4j
//@Order(1)
public class FirebaseTokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        log.info("Logging filter invoked...");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader(AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(httpRequest, response);
            return;
        }

        try {
            String token = authHeader.substring(7);
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            httpRequest.setAttribute(FirebaseConstant.ATTRIBUTE, decodedToken); // firebaseAttribute // token
            log.info("request info: {}", httpRequest.getAttribute(FirebaseConstant.ATTRIBUTE));
            String uid = decodedToken.getUid();

            UserRecord user = FirebaseAuth.getInstance().getUser(uid);
////            UserInfo[] userInfos = user.getProviderData();
//
//            log.info("user data {}", (Object) userInfos);
            Map<String, Object> claims = user.getCustomClaims();

            List<GrantedAuthority> authorities = new ArrayList<>();
            String role = (String) claims.get("role");
            if (role != null) {
                authorities.add(new SimpleGrantedAuthority("role"));
            }

            UsernamePasswordAuthenticationToken authentication
                    = new UsernamePasswordAuthenticationToken(uid, null, authorities);
            log.info("authentication {}", authentication);
            log.info("user {}", user.getCustomClaims());
            log.info("authorities {}", authorities.stream().toList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            chain.doFilter(httpRequest, response);
        }

    }
}

