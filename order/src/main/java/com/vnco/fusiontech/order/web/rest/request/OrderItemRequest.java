package com.vnco.fusiontech.order.web.rest.request;

public record OrderItemRequest(
        Long variantId,
        Integer quantity,
        Double price
) {
}
