package com.vnco.fusiontech.product.web.rest.request;

import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Category;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateProductRequest(
                String name,
                String slug,
                Category category,
                Brand brand,
                List<String> images,
                String summary,
                String description,
                List<String> features,
                List<ListSpecificationRequest> specifications) {
}
