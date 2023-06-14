package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.common.service.PublicProductVariantService;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.ProductVariant;

import java.util.List;

public interface ProductVariantService extends PublicProductVariantService {
    List<ProductVariant> getAllProductVariants();
    ProductVariant getProductVariantById(int id);
    ProductVariant createProductVariant(ProductVariant productVariant);
    ProductVariant updateProductVariant(ProductVariant productVariant);
    void deleteProductVariant(int id);
}
