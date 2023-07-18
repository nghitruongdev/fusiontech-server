package com.vnco.fusiontech.product.web.rest.request;

import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Category;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record CreateProductRequest(
    String name,
    String slug,
    Category category,
    Brand brand,
    String thumbnail,
    String summary,
    String description,
    List<String> features,
    Map<String, String> specifications,
    List<ProductAttributeRequest> attributes) {
}