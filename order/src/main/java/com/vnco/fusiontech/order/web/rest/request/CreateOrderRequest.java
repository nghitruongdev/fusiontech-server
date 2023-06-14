package com.vnco.fusiontech.order.web.rest.request;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        String email,
        String note,
        UUID userId,
        Long addressId,
        List<OrderItemRequest> items
) {

}
