package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.DTO.ProductDTO;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.service.ProductService;
import com.vnco.fusiontech.product.web.rest.request.AddProductReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

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

    // xem danh sách sản phẩm theo danh mục
    @GetMapping("/findByCategory/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable int categoryId) {
        List<ProductDTO> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }


}
