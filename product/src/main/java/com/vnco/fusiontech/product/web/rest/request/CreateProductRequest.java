package com.vnco.fusiontech.product.web.rest.request;

import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Category;

import java.util.List;

public record CreateProductRequest(
        String name,
        String description,
        Category category,
        Brand brand,
        List<ProductAttributeRequest> attributes) {
}
