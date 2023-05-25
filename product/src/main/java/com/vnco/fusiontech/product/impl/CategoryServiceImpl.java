package com.vnco.fusiontech.product.impl;

import com.vnco.fusiontech.product.entity.Category;
import com.vnco.fusiontech.product.repository.CategoryRepository;
import com.vnco.fusiontech.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Category category, Integer id) {
        /*
        - tìm category dựa vào id -> nếu tìm thấy sẽ lưu vào existingCategory, nếu không thì existingCategory sẽ null
        - ktra existingCategory khác null hay không
            + Khác: category_id đã tồn tại trong DB -> lấy thông tin của category_id này bằng setName
            + Null: ko tìm thấy category trong DB và trả về null
        * */
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory != null) {
            existingCategory.setName(category.getName());
            return categoryRepository.save(existingCategory);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);

    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
