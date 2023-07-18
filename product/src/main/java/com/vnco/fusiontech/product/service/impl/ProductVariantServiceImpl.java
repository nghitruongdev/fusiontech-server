package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.common.service.PublicOrderService;
import com.vnco.fusiontech.product.entity.Variant;
import com.vnco.fusiontech.product.entity.VariantInventory;
import com.vnco.fusiontech.product.repository.ProductVariantRepository;
import com.vnco.fusiontech.product.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository repository;

    private PublicOrderService orderService;

    @Autowired
    @Lazy
    public void setOrderService(PublicOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public List<Variant> getAllProductVariants() {
        return repository.findAll();
    }

    @Override
    public Variant getProductVariantById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Variant createProductVariant(Variant productVariant) {
        return repository.save(productVariant);
    }

    @Override
    public Variant updateProductVariant(Variant productVariant) {
        return repository.save(productVariant);
    }

    @Override
    public void deleteProductVariant(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalQuantity(Long variantId) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public long getAvailableQuantity(Long variantId) {
        return orderService.getAvailableQuantity(variantId);
    }

    @Override
    public void addInventory(Long variantId, VariantInventory inventory) {
        throw new UnsupportedOperationException();
    }
}
