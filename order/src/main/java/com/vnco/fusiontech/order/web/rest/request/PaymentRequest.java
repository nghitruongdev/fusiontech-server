package com.vnco.fusiontech.order.web.rest.request;

import com.vnco.fusiontech.order.entity.PaymentMethod;
import com.vnco.fusiontech.order.entity.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public record PaymentRequest(
        @NotNull @Positive Double amount,
        PaymentMethod method,
        PaymentStatus status,
        Instant paidAt
) {
    public PaymentRequest {
        if (method == null) method = PaymentMethod.COD;
        if (status == null) status = PaymentStatus.UNPAID;
        if (status == PaymentStatus.PAID) {
            paidAt = Instant.now();
        }
    }
}
