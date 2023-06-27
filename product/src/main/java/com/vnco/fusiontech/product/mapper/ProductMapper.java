package com.vnco.fusiontech.product.mapper;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.web.rest.request.CreateProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    
    public Product toProduct(CreateProductRequest request) {
        var product = Product.builder()
                             .name(request.name())
                             .description(request.description())
                             .brand(request.brand())
                             .category(request.category())
                             .build();
        return product;
    }
}
