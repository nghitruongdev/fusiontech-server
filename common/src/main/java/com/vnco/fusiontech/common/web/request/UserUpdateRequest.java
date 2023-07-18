package com.vnco.fusiontech.common.web.request;


public record UserUpdateRequest(

        String firstName,
        String lastName,
        String password,
        String email,
        String phoneNumber,
        String photoUrl
) {
}
