package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.entity.Category;
import com.vnco.fusiontech.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/categories")
//!todo: change the mapping to ("/api/...") -> done
public class CategoryRestController {
    @Autowired
    CategoryService categoryService;

    @GetMapping()
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    @GetMapping("/findCategoryById/{id}")
    public Category findById(@PathVariable int id) {
        return categoryService.findById(id);
    }

    @PostMapping("/saveCategory")
    public Category save(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping("/updateCategory/{id}")
    public Category update(@PathVariable("id") Integer id, @RequestBody Category category) {
        return categoryService.update(category, id);
    }

    @DeleteMapping("/deleteCategory/{id}")
    public void delete(@PathVariable("id") Integer id) {
        categoryService.delete(id);
    }
}
