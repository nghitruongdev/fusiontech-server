package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.product.entity.ProductVariant;
import com.vnco.fusiontech.product.repository.ProductVariantRepository;
import com.vnco.fusiontech.product.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {
    
    private final ProductVariantRepository productVariantRepository;

    @Override
    public List<ProductVariant> getAllProductVariants() {
        return productVariantRepository.findAll();
    }

    @Override
    public ProductVariant getProductVariantById(int id) {
        return productVariantRepository.findById(id).orElse(null);
    }

    @Override
    public ProductVariant createProductVariant(ProductVariant productVariant) {
        return productVariantRepository.save(productVariant);
    }

    @Override
    public ProductVariant updateProductVariant(ProductVariant productVariant) {
        return productVariantRepository.save(productVariant);
    }

    @Override
    public void deleteProductVariant(int id) {
        productVariantRepository.deleteById(id);
    }
}
    
    @Override
    public boolean hasEnoughQuantity(Long variantId, int orderQuantity) {
        //todo: check if variant id exists, otherwise throw NotFoundException
        
        //todo: check if order quantity <= available quantity and return result
        return false;
    }
}
