package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Brand;

import java.util.List;

public interface BrandService {
    Brand save(Brand brand);

    Brand update(Brand brand, Integer id);

    void delete(int id);

    Brand findById(int id);

    List<Brand> findAll();
}
