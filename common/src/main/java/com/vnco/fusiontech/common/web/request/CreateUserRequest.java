package com.vnco.fusiontech.common.web.request;

public record CreateUserRequest(
        String firebaseUid,
        String name,
        String email,
        String phoneNumber,
        String photoUrl
) {
}
