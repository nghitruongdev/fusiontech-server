package com.vnco.fusiontech.product.web.rest.request;

import com.vnco.fusiontech.common.constraint.NullOrNotBlank;
import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Category;
import com.vnco.fusiontech.product.entity.Specification;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public record UpdateProductRequest(
        @NullOrNotBlank String name,
        @NullOrNotBlank String slug,
        Category category,
        Brand brand,
        List<String> images,
        String summary,
        String description,
        List<String> features,
        List<Specification> specifications,
        Boolean active,
        String status,
        Byte discount
) {
    public UpdateProductRequest {
    }
}
