package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.projection.DynamicProductInfo;
import com.vnco.fusiontech.product.entity.projection.ProductSpecificationDTO;
import com.vnco.fusiontech.product.web.rest.request.CreateProductRequest;
import com.vnco.fusiontech.product.web.rest.request.UpdateProductRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    Long createProduct(CreateProductRequest request);

    void updateProduct(Long id, @Valid UpdateProductRequest req);

    void deleteProduct(Long id);

    List<Product> searchProduct(String keyword);

    void addUserFavoriteProduct(Long productId, Long uid);

    void removeUserFavoriteProduct(Long productId, Long uid);

    List<ProductSpecificationDTO> getProductSpecifications(Long productId);
    
    Optional<DynamicProductInfo> getProductDynamicInfo(Long productId);
}
