package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.product.entity.ProductAttribute;
import com.vnco.fusiontech.product.repository.ProductAttributeRepository;
import com.vnco.fusiontech.product.service.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {
    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Override
    public List<ProductAttribute> getAllProductAttributes() {
        return productAttributeRepository.findAll();
    }

    @Override
    public ProductAttribute getProductAttributeById(Long id) {
        return productAttributeRepository.findById(id).orElse(null);
    }

    @Override
    public ProductAttribute createProductAttribute(ProductAttribute productAttribute) {
        return productAttributeRepository.save(productAttribute);
    }

    @Override
    public ProductAttribute updateProductAttribute(ProductAttribute productAttribute) {
        return productAttributeRepository.save(productAttribute);
    }

    @Override
    public void deleteProductAttribute(Long id) {
        productAttributeRepository.deleteById(id);
    }
}
