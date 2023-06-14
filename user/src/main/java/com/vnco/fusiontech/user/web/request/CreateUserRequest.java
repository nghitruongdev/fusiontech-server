package com.vnco.fusiontech.user.web.request;

import jakarta.validation.Valid;

public record CreateUserRequest(@Valid UserInfo userInfo) {
    public CreateUserRequest {

    }
}
