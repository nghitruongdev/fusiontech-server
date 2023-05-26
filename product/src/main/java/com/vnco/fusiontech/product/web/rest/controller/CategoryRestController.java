package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.entity.Category;
import com.vnco.fusiontech.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/categories")
//!todo: change the mapping to ("/api/...")
public class CategoryRestController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/findAll")
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    @GetMapping("/findById/{id}")
    public Category findById(@PathVariable int id) {
        return categoryService.findById(id);
    }

    @PostMapping("/save")
    public Category save(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping("/update/{id}")
    public Category update(@PathVariable("id") Integer id, @RequestBody Category category) {
        return categoryService.update(category, id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        categoryService.delete(id);
    }
}
