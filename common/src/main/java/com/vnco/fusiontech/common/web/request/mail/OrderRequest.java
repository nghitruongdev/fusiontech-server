package com.vnco.fusiontech.common.web.request.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;


@SuperBuilder
@Getter
@Accessors (fluent = true)
public class OrderRequest extends MailRequest {
    @Builder
    public record ProductItem(String image, String name, Double price, Integer quantity, Double discount) {
    }
    
    private final Long              orderId;
    private final String            name;
    private final Double            total;
    private final String            productImageUrl;
    private final List<ProductItem> items = new ArrayList<>();
}
