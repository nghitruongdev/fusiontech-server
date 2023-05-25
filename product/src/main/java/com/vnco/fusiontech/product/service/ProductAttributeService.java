package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.ProductAttribute;

import java.util.List;

public interface ProductAttributeService {
    List<ProductAttribute> getAllProductAttributes();
    ProductAttribute getProductAttributeById(Long id);
    ProductAttribute createProductAttribute(ProductAttribute productAttribute);
    ProductAttribute updateProductAttribute(ProductAttribute productAttribute);
    void deleteProductAttribute(Long id);
}
