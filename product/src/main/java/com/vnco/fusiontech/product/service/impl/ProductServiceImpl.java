package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.VariantAttribute;
import com.vnco.fusiontech.product.entity.Variant;
import com.vnco.fusiontech.product.entity.proxy.User;
import com.vnco.fusiontech.product.mapper.ProductMapper;
import com.vnco.fusiontech.product.repository.ProductRepository;
import com.vnco.fusiontech.product.service.ProductService;
import com.vnco.fusiontech.product.web.rest.request.CreateProductRequest;
import com.vnco.fusiontech.product.web.rest.request.ProductAttributeRequest;
import com.vnco.fusiontech.product.web.rest.request.UpdateProductRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final PublicUserService userService;
    private final ProductMapper mapper;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Long createProduct(CreateProductRequest request) {
        var product = mapper.toProduct(request);
        var variants = createProductVariants(request.attributes());
        product.setVariants(variants);

        // variants.forEach(variant -> {
        // variant.setActive(true);
        // });
        // todo: should we validate with publish Date?

        return productRepository.save(product).getId();
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchProduct(String keyword) {
        // tìm kiếm sản phẩm dựa trên từ khóa (keyword)
        List<Product> products = productRepository.searchByKeyword(keyword);
        return products;
    }

    @Override
    public void addUserFavoriteProduct(@NonNull Long productId, @NonNull Long uid) {
        //completed: whatif product not exists
        //completed: whatif product has exists

        // todo: whatif user has not liked this product before
        // todo: whatif user already liked this product before

        var product = validateFavoriteRequest(productId, uid);
        if (product.favoritesContains(uid)) {
            throw new InvalidRequestException("Product is already in user's favorites");
        }
        product.addFavoriteUser(new User(uid));
    }

    @Override
    public void removeUserFavoriteProduct(@NonNull Long productId, @NonNull Long uid) {
        var product = validateFavoriteRequest(productId, uid);
        if (!product.favoritesContains(uid)) {
            throw new InvalidRequestException("Product is not in user's favorites");
        }
        product.removeFavoriteUser(uid);
    }

    private Product validateFavoriteRequest(Long productId, Long uid) {
        //todo: find product, if not exists throw exception
        var product = productRepository.findById(productId).orElseThrow(RecordNotFoundException::new);

        // todo: findUser, if not found throw exception
        var isUserExists = userService.existsById(uid);
        if (!isUserExists)
            throw new RecordNotFoundException("No user was found with the given id");
        return product;
    }

    private List<Variant> createProductVariants(@NotEmpty List<ProductAttributeRequest> attributes) {
        var localAttributes = new ArrayList<>(attributes);
        var firstGroup = localAttributes.remove(0);

        BiFunction<String, String, VariantAttribute> toProductAttribute = (name, value) -> VariantAttribute.builder()
                .name(name)
                .value(value)
                .build();

        var baseList = new ArrayList<>(
                firstGroup.values().stream().map(value -> toProductAttribute.apply(firstGroup.name(), value))
                        .map(attribute -> new LinkedList<>(List.of(attribute))).toList());
        localAttributes.forEach((group) -> {
            var result = baseList.stream().flatMap(
                    base -> group.values().stream().map(value -> {
                        var list = new LinkedList<>(base);
                        list.add(toProductAttribute.apply(group.name(), value));
                        return list;
                    })).toList();
            baseList.clear();
            baseList.addAll(result);
        });

        return baseList.stream().map((list) -> {
            var variant = new Variant();
            variant.setAttributes(list);
            return variant;
        }).toList();
    }

    @Override
    public void updateProduct(Long id, @Valid UpdateProductRequest req) {
        var product = productRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        mapper.partialUpdateProduct(req, product);
    }
}
