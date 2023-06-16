package com.vnco.fusiontech.common.web.request;

import lombok.Builder;

@Builder
public record RegisterRequest(
        String username,
        String password,
        String email,
        String fistName,
        String lastName,
        String phone
) {
}
