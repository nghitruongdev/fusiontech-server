package com.vnco.fusiontech.user.web.request;

import com.vnco.fusiontech.common.constant.AuthoritiesConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;


public record UserInfo(
        @NotBlank String username,
        @NotBlank String passwordHash,
        @Email @NotBlank String email,
        @NotBlank String phone,
        List<String> authorities
) {
    public UserInfo {
        if (authorities == null || authorities.isEmpty()) {
            authorities = new ArrayList<>(List.of(AuthoritiesConstant.USER));
        }
    }
}
