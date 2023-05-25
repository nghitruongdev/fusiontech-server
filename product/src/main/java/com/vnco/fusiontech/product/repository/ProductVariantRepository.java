package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {

}