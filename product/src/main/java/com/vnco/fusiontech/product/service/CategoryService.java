package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Category;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.web.request.NewProductRequest;
import com.vnco.fusiontech.product.web.request.UpdateProductRequest;

import java.util.List;

public interface CategoryService {
    Long save(Category category);
    
    void update(Category category);
    
    void delete(Long id);
    
    Category findById(Long id);
    
    List<Category> findAll();
}
