package com.vnco.fusiontech.common.web.request.mail;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.List;


@SuperBuilder
@Getter
@Accessors (fluent = true)
public class OrderRequest extends MailRequest {
    
    private final Long                   orderId;
    private final String                 name;
    private final Double                 total;
    private final String                 productImageUrl;
    private final List<OrderItemDTO> items;
}
