package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {

}
