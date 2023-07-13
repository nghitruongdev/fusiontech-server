package com.vnco.fusiontech.common.web.request;

import java.util.Optional;

public record UserUpdateRequest(

        Optional<String> name,
        Optional<String> email,
        Optional<String> phoneNumber,
        Optional<String> photoUrl
) {
}
