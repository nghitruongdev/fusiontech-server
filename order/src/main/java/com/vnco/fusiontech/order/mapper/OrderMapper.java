package com.vnco.fusiontech.order.mapper;

import com.vnco.fusiontech.order.entity.*;
import com.vnco.fusiontech.order.web.rest.request.CreateOrderRequest;
import com.vnco.fusiontech.order.web.rest.request.OrderItemRequest;
import com.vnco.fusiontech.order.web.rest.request.PaymentRequest;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    
    public Order toOrder(CreateOrderRequest request) {
        Set<OrderItem> items   = request.items().stream().map(this::toOrderItem).collect(Collectors.toSet());
        var            payment = toPayment(request.payment());
        var order = Order.builder()
                         .email(request.email())
                         .note(request.note())
                         .userId(request.userId())
                         .addressId(request.addressId())
                         .payment(payment)
                         .status(request.status())
                         .voucher(request.voucher())
                         .build();
        order.setOrderItems(items);
        return order;
    }
    
    
    OrderItem toOrderItem(OrderItemRequest request) {
        return OrderItem.builder()
                        .variant(new OrderVariant(request.variantId()))
                        .price(request.price())
                        .quantity(request.quantity())
                        .build();
    }
    
    Payment toPayment(PaymentRequest request) {
        return Payment.builder()
                      .amount(request.amount())
                      .status(request.status())
                      .method(request.method())
                      .paidAt(request.paidAt())
                      .build();
    }
    
}
