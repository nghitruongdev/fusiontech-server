package com.vnco.fusiontech.common.web.request;

import com.vnco.fusiontech.common.utils.FirebaseUtils;

public record RegisterUserWithEmailRequest(
        String firstName,
        String lastName,
        String displayName,
        String phone,
        String email,
        String password
) {
    public RegisterUserWithEmailRequest {
        if(phone != null){
            phone = FirebaseUtils.convertToE164Format(phone);
        }
        if(displayName == null){
            displayName = FirebaseUtils.composeFullName(firstName, lastName);
        }
        
    }
}
