package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Attribute;
import com.vnco.fusiontech.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AttributeRepository extends JpaRepository<Attribute, Integer> {

}
