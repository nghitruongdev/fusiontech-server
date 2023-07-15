package com.vnco.fusiontech.security.service.impl;

import com.google.firebase.auth.FirebaseToken;
import com.vnco.fusiontech.common.constant.FirebaseConstant;
import com.vnco.fusiontech.security.service.FirebaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class FirebaseServiceImpl implements FirebaseService {
    @Override
    public FirebaseToken decodedToken(HttpServletRequest request) {
        return (FirebaseToken) request.getAttribute(FirebaseConstant.ATTRIBUTE);
    }
}
