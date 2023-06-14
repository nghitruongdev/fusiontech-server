package com.vnco.fusiontech.order.web.rest.request;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        UUID userId,
        Long addressId,
        List<OrderItemRequest> items
) {

}
