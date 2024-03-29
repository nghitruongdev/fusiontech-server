package com.vnco.fusiontech.order.web.rest.request;

import com.vnco.fusiontech.common.constant.PaymentMethod;
import com.vnco.fusiontech.common.constant.PaymentStatus;

import java.time.LocalDateTime;

public record PaymentRequest(
        Double amount,
        PaymentMethod method,
        PaymentStatus status,
        LocalDateTime paidAt
) {
    public PaymentRequest {
        if (method == null) method = PaymentMethod.CASH;
        if (status == null) status = PaymentStatus.PENDING;
        if (status == PaymentStatus.COMPLETED) {
            paidAt = LocalDateTime.now();
        }
    }
}
