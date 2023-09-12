package com.vnco.fusiontech.order.exception;

import com.vnco.fusiontech.common.exception.InvalidRequestException;

public class InsufficientQuantityException extends InvalidRequestException {
   
    public InsufficientQuantityException(Long variantId) {
        super("Số lượng tồn kho không đủ cho sản phẩm " + variantId);
    }
}
