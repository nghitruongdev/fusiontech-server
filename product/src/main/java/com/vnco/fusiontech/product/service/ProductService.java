package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Product;


import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(int id);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(int id);
    List<Product> searchProduct(String keyword);

}
