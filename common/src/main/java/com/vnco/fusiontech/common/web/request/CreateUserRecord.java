package com.vnco.fusiontech.common.web.request;


public record CreateUserRecord(
        String firebaseUid,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String photoUrl
) {
    public CreateUserRecord {
    
    }
}
