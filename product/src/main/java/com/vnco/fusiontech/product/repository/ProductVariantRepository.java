package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.common.web.response.VariantWithProductInfoDTO;
import com.vnco.fusiontech.product.entity.Variant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "variants", itemResourceRel = "variant", collectionResourceRel = "variants")
public interface ProductVariantRepository extends JpaRepository<Variant, Long> {
    
    @RestResource (path = "many", rel = "many")
    List<Variant> findAllByIdIn(@Param ("ids") List<Long> ids);
    
    @RestResource
    boolean existsBySku(@Param ("sku") String sku);
    
    @RestResource
    Optional<Variant> findBySku(@Param ("sku") String sku);
    
    @Query (
            """
            select new com.vnco.fusiontech.common.web.response.VariantWithProductInfoDTO(
            v.id,
            COALESCE(v.images, v.product.images),
            v.product.name,
            v.sku,
            v.price,
            v.product.discount
            ) FROM Variant v
            WHERE v.id IN :ids
            """
    )
    List<VariantWithProductInfoDTO> findVariantsWithProductInfo(@Param ("ids") List<Long> ids);
}
