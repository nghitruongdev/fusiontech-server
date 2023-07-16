package com.vnco.fusiontech.common.web.request;

import java.util.Optional;

public record UserUpdateRequest(

        String firstName,
        String lastName,
        String password,
        String email,
        String phoneNumber,
        String photoUrl
) {
}
