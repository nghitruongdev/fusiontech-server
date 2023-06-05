package com.vnco.fusiontech.user.web.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record UserInfo(
        @NotBlank String username,
        @NotBlank String passwordHash,
        @Email @NotBlank String email,
        @NotBlank String phone,
        List<String> authorities
) {
}
