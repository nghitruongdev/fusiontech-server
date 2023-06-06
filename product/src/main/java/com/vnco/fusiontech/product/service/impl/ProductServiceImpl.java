package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.product.DTO.ProductDTO;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.repository.ProductRepository;
import com.vnco.fusiontech.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> searchProduct(String keyword) {
        // tìm kiếm sản phẩm dựa trên từ khóa (keyword)
        List<Product> products = productRepository.searchByKeyword(keyword);
        return products;
    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(int categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        // Chuyển đổi từ đối tượng Product sang ProductDTO
        List<ProductDTO> productDTOs = products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return productDTOs;
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        // Thiết lập các thuộc tính khác của productDTO

        return productDTO;
    }

}