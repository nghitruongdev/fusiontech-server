package com.vnco.fusiontech.order.service;

import com.vnco.fusiontech.common.constant.OrderStatus;
import com.vnco.fusiontech.order.web.rest.request.CreateOrderRequest;

import java.util.UUID;

public interface OrderService {
    UUID createOrder(CreateOrderRequest request);
    void updateOrderStatus(UUID oid, OrderStatus newStatus);
    void cancelOrder(UUID oid);
}
