package com.vnco.fusiontech.product.web.rest.request;

import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateProductRequest(
        @NotBlank String name,
        @NotBlank String slug,
        Category category,
        Brand brand,
        List<String> images,
        String summary,
        String description,
        Boolean active,
        String status,
        Byte discount,
        Double price,
        List<String> features,
        List<ListSpecificationRequest> specifications) {
    public CreateProductRequest{
        if(price == null) price = 0D;
    }
}
