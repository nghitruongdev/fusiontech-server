package com.vnco.fusiontech.order.service;

import com.vnco.fusiontech.common.service.PublicOrderService;
import com.vnco.fusiontech.common.constant.OrderStatus;
import com.vnco.fusiontech.order.web.rest.request.CreateOrderRequest;
import jakarta.validation.Valid;

public interface OrderService extends PublicOrderService {
    Long createOrder(@Valid CreateOrderRequest request);
    
    void updateOrderStatus(Long oid, OrderStatus newStatus);

    Long countOrderByStatus(OrderStatus status);
}
