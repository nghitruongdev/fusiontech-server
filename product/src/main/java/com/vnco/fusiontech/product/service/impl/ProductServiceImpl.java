package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.proxy.User;
import com.vnco.fusiontech.product.repository.ProductRepository;
import com.vnco.fusiontech.product.service.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final PublicUserService userService;
    
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    @Override
    public Product getProductById(Long id) {
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
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    @Override
    @Transactional (readOnly = true)
    public List<Product> searchProduct(String keyword) {
        // tìm kiếm sản phẩm dựa trên từ khóa (keyword)
        List<Product> products = productRepository.searchByKeyword(keyword);
        return products;
    }
    
    @Override
    public void addUserFavoriteProduct(@NonNull Long productId, @NonNull UUID uid) {
        //completed: whatif product not exists
        //completed: whatif product has exists
        
        //todo: whatif user has not liked this product before
        //todo: whatif user already liked this product before
        
        var product = validateFavoriteRequest(productId, uid);
        if(product.favoritesContains(uid)){
            throw new InvalidRequestException("Product is already in user's favorites");
        }
        product.addFavoriteUser(new User(uid));
    }
    
    @Override
    public void removeUserFavoriteProduct(@NonNull Long productId, @NonNull UUID uid) {
        var product = validateFavoriteRequest(productId, uid);
        if(!product.favoritesContains(uid)){
            throw new InvalidRequestException("Product is not in user's favorites");
        }
        product.removeFavoriteUser(uid);
    }
    
    private Product validateFavoriteRequest(Long productId, UUID uid) {
        //todo: find product, if not exists throw exception
        var product = productRepository.findById(productId).orElseThrow(RecordNotFoundException::new);
        
        //todo: findUser, if not found throw exception
        var isUserExists = userService.existsById(uid);
        if (!isUserExists) throw new RecordNotFoundException("No user was found with the given id");
        return product;
    }
    
}
