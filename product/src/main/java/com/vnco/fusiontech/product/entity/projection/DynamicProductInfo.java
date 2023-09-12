package com.vnco.fusiontech.product.entity.projection;


public record DynamicProductInfo(
        Double discount,
        String status,
        Boolean active,
        Integer availableQuantity,
        Double minPrice,
        Double maxPrice
) {

}
