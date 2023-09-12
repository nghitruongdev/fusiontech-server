package com.vnco.fusiontech.order.web.rest.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderItemRequest(
        @NotNull Long variantId,
        @NotNull @Positive Integer quantity,
        @NotNull @PositiveOrZero Double price,
        @PositiveOrZero Byte discount
) {
}
