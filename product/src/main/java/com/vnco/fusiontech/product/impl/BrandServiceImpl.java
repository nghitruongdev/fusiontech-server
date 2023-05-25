package com.vnco.fusiontech.product.impl;

import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.repository.BrandRepository;
import com.vnco.fusiontech.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepository brandRepository;

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(Brand brand, Integer id) {
        Brand existingBrand = brandRepository.findById(id).orElse(null);
        if (existingBrand != null) {
            existingBrand.setName(brand.getName());
            return brandRepository.save(existingBrand);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        brandRepository.deleteById(id);
    }

    @Override
    public Brand findById(int id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }
}
