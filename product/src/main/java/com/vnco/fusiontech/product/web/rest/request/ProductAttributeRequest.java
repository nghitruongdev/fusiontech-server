package com.vnco.fusiontech.product.web.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
@Deprecated
public record ProductAttributeRequest(
        @NotBlank String name,
        @NotEmpty List<String> values
) {
}
