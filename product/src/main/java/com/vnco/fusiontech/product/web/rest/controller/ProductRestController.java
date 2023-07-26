package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.service.ProductService;
import com.vnco.fusiontech.product.web.rest.request.CreateProductRequest;
import com.vnco.fusiontech.product.web.rest.request.UpdateProductRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RepositoryRestController
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    // them moi san pham
    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductRequest request) {
        var id = productService.createProduct(request);
        return ResponseEntity.ok(id);
    }

    @PatchMapping("products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id,
            @Valid @NotNull @RequestBody UpdateProductRequest req) {
        productService.updateProduct(id, req);
        return ResponseEntity.ok().build();
    }

    // xoa san pham
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PostMapping("/products/{id}/favorites")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> addFavorite(@PathVariable("id") Long productId, @RequestParam("uid") Long uid) {
        productService.addUserFavoriteProduct(productId, uid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}/favorites")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> removeFavorite(@PathVariable("id") Long productId, @RequestParam("uid") Long uid) {
        productService.removeUserFavoriteProduct(productId, uid);
        return ResponseEntity.ok().build();
    }
}
