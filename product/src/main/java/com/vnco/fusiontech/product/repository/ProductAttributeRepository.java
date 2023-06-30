package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.VariantAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeRepository extends JpaRepository<VariantAttribute, Integer> {

}
