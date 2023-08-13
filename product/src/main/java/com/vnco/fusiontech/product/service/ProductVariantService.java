package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.common.service.PublicProductVariantService;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Variant;
import com.vnco.fusiontech.product.entity.VariantInventory;
import com.vnco.fusiontech.product.web.rest.request.VariantRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ProductVariantService extends PublicProductVariantService {
    List<Variant> getAllProductVariants();

    Variant getProductVariantById(Long id);

    Variant createVariant(VariantRequest request);

    Variant updateProductVariant(Variant productVariant);

    void deleteProductVariant(Long id);

    void addInventory(Long variantId, VariantInventory inventory);

    long getTotalQuantity(Long variantId);

    long getAvailableQuantity(Long variantId);
    
    void updateVariant(Long id, VariantRequest request);
    
    void generateSku(List<Variant> variants);
    
    void generateSku(Product product);
}
