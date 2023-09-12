package com.vnco.fusiontech.product.web.rest.request.mapper;

import com.vnco.fusiontech.product.entity.Variant;
import com.vnco.fusiontech.product.web.rest.request.VariantRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring",collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN, unmappedSourcePolicy = ReportingPolicy.WARN)
public interface VariantMapper {

    @Mapping(target = "inventories", ignore = true)
    @Mapping(target = "id", ignore = true)
    Variant toVariant(VariantRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "inventories", ignore = true)
    @Mapping(target = "specifications", ignore = true)
    void partialUpdateVariant(VariantRequest req, @MappingTarget Variant entity);
    
}
