package com.vnco.fusiontech.order.web.rest.request;

import com.vnco.fusiontech.order.entity.OrderStatus;
import com.vnco.fusiontech.order.entity.PaymentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        String email,
        String note,
        @NotNull UUID userId,
        @NotNull Long addressId,
        @NotNull @Valid PaymentRequest payment,
        OrderStatus status,
        @NotEmpty @Validated @Valid List<OrderItemRequest> items
) {
    public CreateOrderRequest {
        if (status == null) {
            if (payment != null && PaymentStatus.DA_THANH_TOAN.equals(payment.status())) {
                status = OrderStatus.VERIFIED;
            } else {
                status = OrderStatus.PLACED;
            }
        }
    }
}
