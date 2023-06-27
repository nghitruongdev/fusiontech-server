package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.web.rest.request.CreateProductRequest;


import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getAllProducts();
    
    Product getProductById(Long id);
    
    Long createProduct(CreateProductRequest request);
    
    Product updateProduct(Product product);
    void deleteProduct(Long id);
    List<Product> searchProduct(String keyword);
    
    void addUserFavoriteProduct(Long productId, UUID uid);
    void removeUserFavoriteProduct(Long productId, UUID uid);
}
