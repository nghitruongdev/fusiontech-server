package com.vnco.fusiontech.common.web.request.mail;

import lombok.experimental.SuperBuilder;


@SuperBuilder
public class OrderRequest extends MailRequest{
    private final Long orderId;
    private final String name;
    private final Double total;
}
