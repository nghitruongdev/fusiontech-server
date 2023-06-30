package com.vnco.fusiontech.product.repository;


import com.vnco.fusiontech.product.entity.VariantInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource (
        path = "inventories",
        itemResourceRel = "inventory",
        collectionResourceRel = "inventories"
)
public interface VariantInventoryRepository extends JpaRepository<VariantInventory, Long> {
    
    @Query (
            """
            SELECT inventory
            FROM VariantInventory  inventory
            JOIN VariantInventoryDetail detail
            ON inventory.id = detail.inventory.id
            WHERE detail.variant.id =: variantId
            """
    )
    List<VariantInventory> findAllByVariantId(@Param ("vid") Long variantId);
    
    @Query("SELECT COALESCE(SUM (detail.quantity), 0) FROM VariantInventoryDetail detail WHERE detail.inventory" +
           ".id=:inventoryId")
    Integer getTotalQuantity(Long inventoryId);
    
    //todo: add admin can update and delete inventory record
    @Override
    @RestResource (exported = false)
    void deleteById(Long inventoryId);
    
    @Override
    @RestResource (exported = false)
    void delete(VariantInventory entity);
    
}
