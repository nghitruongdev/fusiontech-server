package com.vnco.fusiontech.common.web.request.mail;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;


@SuperBuilder
@Getter
@Accessors (fluent = true)
public class OrderRequest extends MailRequest {
    
    private final Long               orderId;
    private final String             name;
    private final Double             subtotal;
    private final Double             orderTotal;
    private final String             phone;
    private final Instant            date;
    private final Double             shipping;
    private final List<OrderItemDTO> items;
    private final String             address;
    private final String             paymentStatus;
    private final String             voucher;
}
