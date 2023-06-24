package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.common.service.PublicProductVariantService;
import com.vnco.fusiontech.product.entity.ProductVariant;
import com.vnco.fusiontech.product.entity.VariantInventory;

import java.util.List;

public interface ProductVariantService extends PublicProductVariantService {
    List<ProductVariant> getAllProductVariants();
    
    ProductVariant getProductVariantById(Long id);
    
    ProductVariant createProductVariant(ProductVariant productVariant);
    
    ProductVariant updateProductVariant(ProductVariant productVariant);
    
    void deleteProductVariant(Long id);
    
    void addInventory(Long variantId, VariantInventory inventory);
    
    long getTotalQuantity(Long variantId);
    
    long getAvailableQuantity(Long variantId);
    
}
