package com.vnco.fusiontech.order.mapper;

import com.vnco.fusiontech.order.entity.*;
import com.vnco.fusiontech.order.web.rest.request.CreateOrderRequest;
import com.vnco.fusiontech.order.web.rest.request.OrderItemRequest;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    
    public Order toOrder(CreateOrderRequest request) {
        Set<OrderItem> items = request.items().stream().map(this::toOrderItem).collect(Collectors.toSet());
        return Order.builder()
                    .email(request.email())
                    .note(request.note())
                    .userId(request.userId())
                    .addressId(request.addressId())
                    //                    .user(new AppUser(request.userId()))
//                    .address(new ShippingAddress(request.addressId()))
                    //                       .total()
                    //                       .status()
                    .items(items)
                    .build();
    }
    
    OrderItem toOrderItem(OrderItemRequest request) {
        return OrderItem.builder()
                        .variant(new ProductVariant(request.variantId()))
                        .price(request.price())
                        .quantity(request.quantity())
                        .build();
    }
    
}
