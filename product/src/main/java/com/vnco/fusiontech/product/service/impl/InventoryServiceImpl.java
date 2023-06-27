package com.vnco.fusiontech.product.service.impl;


import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.product.entity.VariantInventory;
import com.vnco.fusiontech.product.repository.VariantInventoryRepository;
import com.vnco.fusiontech.product.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final VariantInventoryRepository repository;
    
    @Override
    public Long createInventory(VariantInventory inventory) {
        inventory.setCreatedBy("SYSTEM");
        return repository.save(inventory).getId();
    }
    
    @Override
    public void updateInventory(Long inventoryId, VariantInventory inventory) {
        var updateInventory = repository.findById(inventoryId)
                                        .orElseThrow(RecordNotFoundException::new);
        updateInventory.setLastModifiedBy("SYSTEM");
        updateInventory.setPrice(inventory.getPrice());
        updateInventory.setQuantity(inventory.getQuantity());
        //        updateInventory.setVariant(inventory.getVariant());
        inventory.getVariant().addInventory(updateInventory);
    }
}