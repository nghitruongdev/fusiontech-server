package com.vnco.fusiontech.user.web.request;

import jakarta.validation.Valid;

public record UpdateUserRequest(@Valid UserInfo userInfo) {

}
