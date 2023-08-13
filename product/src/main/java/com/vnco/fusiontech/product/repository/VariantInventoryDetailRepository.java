package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.VariantInventoryDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource (
        path = "inventory-details",
        collectionResourceRel = "inventory-details",
        itemResourceRel = "inventory-detail",
        exported = true)
public interface VariantInventoryDetailRepository extends CrudRepository<VariantInventoryDetail, Long> {
}
