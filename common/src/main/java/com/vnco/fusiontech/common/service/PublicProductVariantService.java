package com.vnco.fusiontech.common.service;

public interface PublicProductVariantService {
    boolean hasEnoughQuantity(Long variantId, int orderQuantity);
}
