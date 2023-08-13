package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.VariantInventory;
import com.vnco.fusiontech.product.entity.VariantInventoryDetail;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;


@Validated

public interface InventoryService {
    Long createInventory(@Valid VariantInventory inventory);
    
    void updateItem(Long itemId, VariantInventoryDetail detail);
    
    void deleteItem(Long itemId);
}
