package com.vnco.fusiontech.order.web.rest.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequest(
        @NotNull Long variantId,
        @NotNull @Positive Integer quantity,
        @NotNull @Positive Double price
) {
}
