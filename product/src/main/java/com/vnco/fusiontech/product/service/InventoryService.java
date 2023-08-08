package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.VariantInventory;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;


@Validated

public interface InventoryService {
    Long createInventory(@Valid VariantInventory inventory);
    
}
