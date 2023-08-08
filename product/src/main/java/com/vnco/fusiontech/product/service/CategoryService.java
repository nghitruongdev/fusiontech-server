package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category category);

    Category update(Category category);
    
    void delete(Long id);
    
    Category findById(Long id);

    List<Category> findAll();
}
