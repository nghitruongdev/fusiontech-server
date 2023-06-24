package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource (path = "variants", itemResourceRel = "variant", collectionResourceRel = "variants")
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {


}
