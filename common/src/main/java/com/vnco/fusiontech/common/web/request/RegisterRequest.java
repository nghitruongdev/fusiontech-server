package com.vnco.fusiontech.common.web.request;

public record RegisterRequest(
        String username,
        String password,
        String email,
        String firstName,
        String lastName,
        String phone
) {
}
