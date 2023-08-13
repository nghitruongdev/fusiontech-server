package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.entity.VariantInventory;
import com.vnco.fusiontech.product.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RepositoryRestController
public class VariantInventoryRestController {
    private final InventoryService service;
    
    @PostMapping ("/inventories")
    @ResponseStatus (HttpStatus.CREATED)
    public ResponseEntity<?> createInventory(@Valid @RequestBody VariantInventory inventory) {
        var created = service.createInventory(inventory);
        return ResponseEntity.ok(created);
    }
    
   

//    @DeleteMapping ("/inventories/{id}")
//    public ResponseEntity<?> deleteInventory(@PathVariable Long id) {
//        return ResponseEntity.ok().build();
//    }
}
