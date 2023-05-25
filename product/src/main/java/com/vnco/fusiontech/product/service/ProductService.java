package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.repository.ProductRepository;
import com.vnco.fusiontech.product.web.rest.request.NewProductRequest;
import com.vnco.fusiontech.product.web.rest.request.UpdateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(int id);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(int id);
}
