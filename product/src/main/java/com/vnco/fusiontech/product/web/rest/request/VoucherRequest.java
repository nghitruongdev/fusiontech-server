package com.vnco.fusiontech.product.web.rest.request;

import java.time.Instant;

public record VoucherRequest(
        String code,
        Double discount,

        Double minOrderAmount,
        Double maxDiscountAmount,
        Instant startDate,
        Instant expirationDate,
        Boolean active

) {
}
