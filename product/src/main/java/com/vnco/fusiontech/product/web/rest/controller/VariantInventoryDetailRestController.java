package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.entity.VariantInventoryDetail;
import com.vnco.fusiontech.product.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RepositoryRestController
public class VariantInventoryDetailRestController {
    
    private final InventoryService service;
    
    @PatchMapping ("/inventory-details/{id}")
    public ResponseEntity<Void> updateItem(@Valid @RequestBody VariantInventoryDetail detail,
                                           @PathVariable Long id
    ) {
        service.updateItem(id, detail);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping ("/inventory-details/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable ("id") Long id) {
        service.deleteItem(id);
        return ResponseEntity.ok().build();
    }
    
}
