package com.vnco.fusiontech.product.web.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ProductAttributeRequest(
        @NotBlank String name,
        @NotEmpty List<String> values
) {
}
