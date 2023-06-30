package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.common.service.PublicProductVariantService;
import com.vnco.fusiontech.product.entity.Variant;
import com.vnco.fusiontech.product.entity.VariantInventory;

import java.util.List;

public interface ProductVariantService extends PublicProductVariantService {
    List<Variant> getAllProductVariants();
    
    Variant getProductVariantById(Long id);
    
    Variant createProductVariant(Variant productVariant);
    
    Variant updateProductVariant(Variant productVariant);
    
    void deleteProductVariant(Long id);
    
    void addInventory(Long variantId, VariantInventory inventory);
    
    long getTotalQuantity(Long variantId);
    
    long getAvailableQuantity(Long variantId);
    
}
