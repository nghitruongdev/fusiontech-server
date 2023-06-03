package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/brands")
public class BrandRestController {
    @Autowired
    BrandService brandService;

    @GetMapping()
    public List<Brand> getAll() {
        return brandService.findAll();
    }

    @GetMapping("/findBrandById/{id}")
    public Brand findById(@PathVariable("id") int id) {
        return brandService.findById(id);
    }

    @PostMapping("/saveBrand")
    public Brand save(@RequestBody Brand brand) {
        return brandService.save(brand);
    }

    @PutMapping("/updateBrand/{id}")
    public Brand update(@PathVariable("id") int id, @RequestBody Brand brand) {
        return brandService.update(brand, id);
    }

    @DeleteMapping("/deleteBrand/{id}")
    public void delete(@PathVariable("id") int id) {
        brandService.delete(id);
    }
}
