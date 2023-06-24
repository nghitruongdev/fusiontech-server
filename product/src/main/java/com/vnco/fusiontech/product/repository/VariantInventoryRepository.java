package com.vnco.fusiontech.product.repository;


import com.vnco.fusiontech.product.entity.VariantInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource (path = "inventories", itemResourceRel = "inventory", collectionResourceRel = "inventories")
public interface VariantInventoryRepository extends JpaRepository<VariantInventory, Long> {
    
    List<VariantInventory> findAllByVariantId(@Param ("vid") Long variantId);
    
    @Override
    @RestResource (exported = false)
    void deleteById(Long inventoryId);
}
