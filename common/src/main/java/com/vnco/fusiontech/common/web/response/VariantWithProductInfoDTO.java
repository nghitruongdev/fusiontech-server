package com.vnco.fusiontech.common.web.response;

import java.util.List;

public record VariantWithProductInfoDTO (
        List<String> images,
        String name,
        Double price,
        Double discount
){

}
