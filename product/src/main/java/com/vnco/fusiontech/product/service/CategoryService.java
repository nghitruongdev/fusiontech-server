package com.vnco.fusiontech.product.service;

import com.github.javafaker.Cat;
import com.vnco.fusiontech.product.entity.Category;

import java.util.List;

public interface CategoryService {

    Category create(Category category);
    
    void delete(Long id);
    Category update(Category category);
    
    Category findById(Long id);

    List<Category> findAll();
}
