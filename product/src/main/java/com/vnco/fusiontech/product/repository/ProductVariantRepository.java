package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Variant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RepositoryRestResource(path = "variants", itemResourceRel = "variant", collectionResourceRel = "variants")
public interface ProductVariantRepository extends JpaRepository<Variant, Long> {

    @RestResource(path = "many", rel = "many")
    List<Variant> findAllByIdIn(@Param("ids") List<Long> ids);
}
