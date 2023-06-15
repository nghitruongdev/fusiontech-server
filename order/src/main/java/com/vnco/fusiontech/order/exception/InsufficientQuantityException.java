package com.vnco.fusiontech.order.exception;

import com.vnco.fusiontech.common.exception.InvalidRequestException;

public class InsufficientQuantityException extends InvalidRequestException {
   
    public InsufficientQuantityException(Long variantId) {
        super("Order quantity exceeds available quantity for product variant with id " + variantId);
    }
}
