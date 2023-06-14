package com.vnco.fusiontech.product.web.rest.controller;
import com.vnco.fusiontech.product.entity.Category;
import com.vnco.fusiontech.product.service.CategoryService;
import com.vnco.fusiontech.product.service.CategoryServiceV1;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/categories")
//!todo: change the mapping to ("/api/...") -> done
public class CategoryRestController {

    @GetMapping
    public List<Category> getAllCategory() {
        throw new NotImplementedException();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable long id) {
        throw new NotImplementedException();
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        throw new NotImplementedException();
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable("id") Integer id,@RequestBody Category category) {
        throw new NotImplementedException();
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable long id) {
        throw new NotImplementedException();
    }

}
