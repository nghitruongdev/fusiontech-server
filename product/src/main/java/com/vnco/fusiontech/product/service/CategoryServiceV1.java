package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Category;

import java.util.List;

@Deprecated
public interface CategoryServiceV1  {
    Category save(Category category);
    
    void delete(int id);
    
    Category findById(int id);
    
    List<Category> findAll();
}
