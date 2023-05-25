package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Attribute;
import com.vnco.fusiontech.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeRepository extends JpaRepository<Attribute, Integer> {

}
