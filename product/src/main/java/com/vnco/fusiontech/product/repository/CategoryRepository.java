package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
    Optional<Category> findBySlug(String slug);
}
