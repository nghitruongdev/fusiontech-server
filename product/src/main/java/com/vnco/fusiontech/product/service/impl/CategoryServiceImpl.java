//package com.vnco.fusiontech.product.service.impl;
//
//import com.vnco.fusiontech.product.entity.Category;
//import com.vnco.fusiontech.product.repository.CategoryRepository;
//import com.vnco.fusiontech.product.service.CategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class CategoryServiceImpl implements CategoryService {
//    @Autowired
//    CategoryRepository categoryRepository;
//    @Override
//    public Category create(Category category) {
//        return categoryRepository.save(category);
//    }
//
//    @Override
//    public Category update(Category category) {
//        return categoryRepository.save(category);
//    }
//
//    @Override
//    public void delete(Long id) {
//        categoryRepository.deleteById(id);
//    }
//
//    @Override
//    public Category findById(Long id) {
//        return categoryRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    public List<Category> findAll() {
//        return categoryRepository.findAll();
//    }
//}
