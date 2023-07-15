package com.vnco.fusiontech.product.mapper;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.web.rest.request.CreateProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    
//    @Mapping (target = "slug", source = "")
    @Mapping (target = "reviewCount", ignore = true)
    @Mapping (target = "favorites", ignore = true)
    @Mapping (target = "avgRating", ignore = true)
    @Mapping (target = "id", ignore = true)
    @Mapping (target = "variants", ignore = true)
    Product toProduct(CreateProductRequest request);
}
