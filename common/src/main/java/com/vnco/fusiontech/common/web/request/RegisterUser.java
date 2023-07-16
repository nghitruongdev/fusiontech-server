package com.vnco.fusiontech.common.web.request;

public record RegisterUser(
        String firstName,
        String lastName,
        String email,
        String password,
        String phone
) {
}
