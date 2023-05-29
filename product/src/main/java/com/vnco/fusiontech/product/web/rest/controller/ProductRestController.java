package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    @Autowired
    ProductService productService;
    // lay tat ca san pham
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // lay san pham theo id
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    // them moi san pham
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // cap nhat san pham theo id
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Integer id,@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    // xoa san pham
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }

    // tim kiem san pham
    @GetMapping("/search")
    public List<Product> searchProduct(@RequestParam("keyword")String keyword){
        List<Product> products = productService.searchProduct(keyword);
        return products;
    }

}
