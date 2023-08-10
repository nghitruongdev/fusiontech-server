package com.vnco.fusiontech.common.web.response;

import java.util.List;

public record VariantWithProductInfoDTO (
        Long id,
        List<String> images,
        String name,
        String sku,
        Double price,
        Integer discount
){
}
