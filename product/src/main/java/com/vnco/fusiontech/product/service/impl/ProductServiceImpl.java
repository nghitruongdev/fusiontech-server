package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.common.utils.BeanUtils;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Specification;
import com.vnco.fusiontech.product.entity.Variant;
import com.vnco.fusiontech.product.entity.projection.DynamicProductInfo;
import com.vnco.fusiontech.product.entity.projection.ProductSpecificationDTO;
import com.vnco.fusiontech.product.entity.proxy.User;
import com.vnco.fusiontech.product.repository.ProductRepository;
import com.vnco.fusiontech.product.service.ProductService;
import com.vnco.fusiontech.product.service.ProductVariantService;
import com.vnco.fusiontech.product.service.SpecificationService;
import com.vnco.fusiontech.product.web.rest.request.CreateProductRequest;
import com.vnco.fusiontech.product.web.rest.request.ListSpecificationRequest;
import com.vnco.fusiontech.product.web.rest.request.UpdateProductRequest;
import com.vnco.fusiontech.product.web.rest.request.mapper.ProductMapper;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository       productRepository;
    private final PublicUserService       userService;
    private final ProductMapper           mapper;
    private final SpecificationService    specificationService;
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public Long createProduct(CreateProductRequest request) {
        var product = mapper.toProduct(request);
        productRepository.save(product);
        var variants = createProductVariant(product, request.specifications());
        variants.forEach(v->{
            v.setPrice(request.price());
        });
        product.setVariants(variants);
        BeanUtils.getBean(ProductVariantService.class)
                .generateSku(variants);
         var specifications = variants.stream().flatMap(variant -> variant.getSpecifications().stream()).toList();
         specificationService.persistSpecifications(specifications);
        return product.getId();
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
    @Transactional(readOnly = true)
    public List<Product> searchProductByCategoryId(Integer cid) {
        // tìm kiếm sản phẩm theo category
        List<Product> products = productRepository.searchByCategoryId(cid);
        return products;
}

    @Override
    public void addUserFavoriteProduct(@NonNull Long productId, @NonNull Long uid) {
        // completed: whatif product not exists
        // completed: whatif product has exists

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
        // todo: find product, if not exists throw exception
        var product = productRepository.findById(productId).orElseThrow(RecordNotFoundException::new);

        // todo: findUser, if not found throw exception
        var isUserExists = userService.existsById(uid);
        if (!isUserExists)
            throw new RecordNotFoundException("No user was found with the given id");
        return product;
    }

    @Override
    public void updateProduct(Long id, @Valid UpdateProductRequest req) {
        var product = productRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        mapper.partialUpdateProduct(req, product);
        updateVariantSpecAfterUpdateProduct(req, product);
    }

    @Override
    @Transactional(readOnly = true)
    //todo: convert to database function
    public List<ProductSpecificationDTO> getProductSpecifications(Long productId) {
        var product = productRepository.findById(productId).orElseThrow();
        var specs = product.getVariants().stream()
                .flatMap(variant -> variant.getSpecifications().stream()).toList();
        var distinctNames = specs.stream().map(Specification::getName).distinct().toList();

        return distinctNames.stream().map(name -> {
            var values = specs.stream().filter(specification -> specification.getName().equals(name)).distinct()
                    .toList();
            return new ProductSpecificationDTO(name, values);
        }).toList();
    }

    @Override
    public Optional<DynamicProductInfo> getProductDynamicInfo(Long productId) {
//        return  Optional.of(productRepository.getDynamicProductInfo(productId));
        throw new UnsupportedOperationException();
    }

    public List<Variant> createProductVariant(Product product, List<ListSpecificationRequest> specs) {
        if (specs == null || specs.isEmpty()) {
            return List.of(Variant.builder().build());
        }

        List<ListSpecificationRequest> multipleValueSpecs = new ArrayList<>();
        List<ListSpecificationRequest> singleValueSpecs = new ArrayList<>();
        specs.forEach(spec -> {
            var list = spec.values();
            if (list == null || list.isEmpty()) {
                throw new RuntimeException("Spec value is not valid");
            }
            if (list.size() == 1) {
                singleValueSpecs.add(spec);
            } else {
                multipleValueSpecs.add(spec);
            }
        });
        var singleValueList = singleValueSpecs.stream().flatMap(spec -> spec.values().stream()).toList();
        var variants = create(multipleValueSpecs);
        variants.forEach(variant -> singleValueList.forEach(variant::addSpecification));
        return variants;
    }

    private List<Variant> create(@NonNull List<ListSpecificationRequest> multiValueSpecs) {
        if (multiValueSpecs.isEmpty())
            return List.of(Variant.builder().build());

        var localList = new ArrayList<>(multiValueSpecs);

        var firstGroup = localList.remove(0);

        var baseList = new ArrayList<>(
                firstGroup.values().stream()
                        .map(spec -> new LinkedList<>(List.of(spec))).toList());

        localList.forEach((group) -> {
            var result = baseList.stream().flatMap(
                    base -> group.values().stream().map(value -> {
                        var list = new LinkedList<>(base);
                        list.add(value);
                        return list;
                    })).toList();
            baseList.clear();
            baseList.addAll(result);
        });

        return baseList.stream().map((list) -> {
            var variant = new Variant();
            list.forEach(variant::addSpecification);
            return variant;
        }).toList();
    }
    
    private void updateVariantSpecAfterUpdateProduct(UpdateProductRequest req, Product product) {
        var reqSpecs = req.specifications();
        if (reqSpecs == null) return;
        
        var removedNames = getProductSpecifications(product.getId())
                                   .stream()
                                   .filter(item -> item.values().size() == 1)
                                   .map(ProductSpecificationDTO::name)
                                   .toList();
        Hibernate.initialize(product.getVariants());
        var variants = product.getVariants();
        specificationService.persistSpecifications(reqSpecs);
        variants.forEach(v -> {
            var removedSpecs =
                    v.getSpecifications().stream().filter(item -> removedNames.contains(item.getName())).toList();
            
            removedSpecs.forEach(v::removeSpecification);
            reqSpecs.forEach(v::addSpecification);
        });
    }
}
