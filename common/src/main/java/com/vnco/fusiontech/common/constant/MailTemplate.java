package com.vnco.fusiontech.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MailTemplate {
    ORDER_SUCCESS("order-success-mail");
    private final String name;
    
}
