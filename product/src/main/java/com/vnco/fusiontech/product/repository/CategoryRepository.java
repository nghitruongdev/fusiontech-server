package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
    Optional<Category> findBySlug(String slug);
    
    @RestResource (path = "all", rel = "all")
    @Query ("from Category ")
    List<Category> findAllWithoutPage();
    
    @RestResource(path = "findByName", rel = "findByName")
    Optional<Category> findFirstByNameEqualsIgnoreCase(@Param("name")String name);
}
