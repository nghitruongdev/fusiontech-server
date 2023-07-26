package com.vnco.fusiontech.product.web.rest.request;

import com.vnco.fusiontech.product.entity.Variant;
import org.mapstruct.*;

@Mapper(componentModel = "spring",collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN, unmappedSourcePolicy = ReportingPolicy.WARN)
public interface VariantMapper {

    @Mapping(target = "inventories", ignore = true)
    @Mapping(target = "id", ignore = true)
    Variant toVariant(VariantRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "inventories", ignore = true)
//    @Mapping(target = "attributes", ignore = true)
    void partialUpdateVariant(VariantRequest req, @MappingTarget Variant entity);
    
//    List<VariantAttribute> toAttributes(List<VariantAttributeRequest> requests);
    
//   default void updateListAttributes(VariantRequest req, @MappingTarget Variant entity){
//       if(req.attributes()==null || req.attributes().isEmpty() ) return;
//       var list = toAttributes(req.attributes());
//        entity.
//    }
}
