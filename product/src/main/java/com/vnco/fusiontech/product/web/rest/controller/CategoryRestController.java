package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.entity.Category;
import com.vnco.fusiontech.product.service.CategoryService;
import com.vnco.fusiontech.product.service.CategoryServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/category")
public class CategoryRestController {

//    @Autowired
//    CategoryServiceV1 categoryService;
//
//    @GetMapping()
//    public List<Category> getAll() {
//        return categoryService.findAll();
//    }


}
