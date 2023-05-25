package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.ProductVariant;

import java.util.List;

public interface ProductVariantService {
    List<ProductVariant> getAllProductVariants();
    ProductVariant getProductVariantById(Long id);
    ProductVariant createProductVariant(ProductVariant productVariant);
    ProductVariant updateProductVariant(ProductVariant productVariant);
    void deleteProductVariant(Long id);
}
