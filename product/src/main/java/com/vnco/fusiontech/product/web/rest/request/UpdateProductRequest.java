package com.vnco.fusiontech.product.web.rest.request;

import java.util.List;
import java.util.Map;

import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Category;

public record UpdateProductRequest(
        String name,
        String slug,
        Category category,
        Brand brand,
        String thumbnail,
        String summary,
        String description,
        List<String> features,
        Map<String, String> specifications) {

}
