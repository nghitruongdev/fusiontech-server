package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.web.request.NewProductRequest;
import com.vnco.fusiontech.product.web.request.UpdateProductRequest;

import java.util.List;

public interface ProductService {

    Long save(NewProductRequest request);
    
    void update(UpdateProductRequest request);
    
    void delete(Long id);
    
    Product findById(Long id);
    
    List<Product> findAll();
}
