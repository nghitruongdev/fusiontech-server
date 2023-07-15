package com.vnco.fusiontech.security.service;

import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;

public interface FirebaseService {
    FirebaseToken decodedToken(HttpServletRequest request);


}
