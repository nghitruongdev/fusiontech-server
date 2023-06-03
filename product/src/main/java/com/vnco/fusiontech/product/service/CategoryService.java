package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category category);

    Category update(Category category, Integer id);

    void delete(int id);

    Category findById(int id);

    List<Category> findAll();
}
