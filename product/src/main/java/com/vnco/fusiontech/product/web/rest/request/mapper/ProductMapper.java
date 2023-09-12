package com.vnco.fusiontech.product.web.rest.request.mapper;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.web.rest.request.CreateProductRequest;
import com.vnco.fusiontech.product.web.rest.request.UpdateProductRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN, unmappedSourcePolicy = ReportingPolicy.WARN)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toProduct(CreateProductRequest request);

    @Mapping(target = "id", ignore = true)
    void partialUpdateProduct(UpdateProductRequest req, @MappingTarget Product entity);

}
