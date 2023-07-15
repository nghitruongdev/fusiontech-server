package com.vnco.fusiontech.product.web.rest.request;

import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Category;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateProductRequest(
        String name,
        Category category,
        Brand brand,
        String thumbnail,
        String shortDescription,
        String description,
        List<ProductAttributeRequest> attributes) {
}
