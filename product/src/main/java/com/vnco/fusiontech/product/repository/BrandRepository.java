package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    
    @RestResource (path = "all", rel = "all")
    @Query ("from Brand ")
    List<Brand> findAllWithoutPage();
}
