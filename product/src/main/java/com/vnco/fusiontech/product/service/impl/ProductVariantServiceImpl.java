package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.common.service.PublicOrderService;
import com.vnco.fusiontech.product.entity.Specification;
import com.vnco.fusiontech.product.entity.Variant;
import com.vnco.fusiontech.product.entity.VariantInventory;
import com.vnco.fusiontech.product.entity.projection.ProductSpecificationDTO;
import com.vnco.fusiontech.product.repository.ProductVariantRepository;
import com.vnco.fusiontech.product.service.ProductService;
import com.vnco.fusiontech.product.service.ProductVariantService;
import com.vnco.fusiontech.product.web.rest.request.VariantRequest;
import com.vnco.fusiontech.product.web.rest.request.mapper.VariantMapper;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository repository;

    private PublicOrderService orderService;

    private final VariantMapper mapper;
    
    private final ProductService productService;

    @Autowired
    @Lazy
    public void setOrderService(PublicOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public List<Variant> getAllProductVariants() {
        return repository.findAll();
    }

    @Override
    public Variant getProductVariantById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Variant createVariant(VariantRequest request) {
        var variant = mapper.toVariant(request);
        var product = productService.getProductById(variant.getProduct().getId());
        product.addVariant(variant);
        var productSpecs = productService.getProductSpecifications(product.getId());
        productSpecs.stream()
                      .filter(spec-> spec.values().size() == 1)
                .forEach(spec-> variant.addSpecification(spec.values().get(0)));
        
        if(variant.getSku() == null){
            generateSku(List.of(variant),productSpecs );
        }
        return repository.save(variant);
    }

    @Override
    public Variant updateProductVariant(Variant productVariant) {
        return repository.save(productVariant);
    }

    @Override
    public void deleteProductVariant(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalQuantity(Long variantId) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public long getAvailableQuantity(Long variantId) {
        return orderService.getAvailableQuantity(variantId);
    }
    
    @Override
    public void updateVariant(Long id, VariantRequest request) {
        var variant = repository.findById(id).orElseThrow();
        mapper.partialUpdateVariant(request, variant);
    }
    
    @Override
    public void addInventory(Long variantId, VariantInventory inventory) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void generateSku(@NotEmpty List<Variant> variants) {
        var names = productService.getProductSpecifications(variants.get(0).getProduct().getId());
        generateSku(variants, names);
    }
    
    private void generateSku(@NotEmpty List<Variant> variants, List<ProductSpecificationDTO> specs){
        var names=   specs.stream()
                              .filter(item -> item.values().size() > 1)
                              .map(ProductSpecificationDTO::name).toList();
        var product = variants.get(0).getProduct();
        Long id = product.getId();
        String name = product.getName().substring(0, 3);
        String brandName = standardizeCode(product.getBrand().getName(), 3);
        String categoryCode = standardizeCode(product.getCategory().toString(), 3);
        Function<Variant, String> getSpecCode = variant ->  variant.getSpecifications().stream()
                                                                   .filter(spec -> names.contains(spec.getName()))
                                                                   .map(this::convertSpecToSkuCode)
                                                                   .collect(Collectors.joining("-"));
        variants.forEach(variant -> {
            var specCode = getSpecCode.apply(variant);
            String sku =  id +  "-" + brandName + "-" + categoryCode + "-" + name + (!specCode.isEmpty()?
                                                                                     "-" + specCode: "");
            variant.setSku(sku);
        });
    }
    
    private String convertSpecToSkuCode(Specification spec){
        String name = spec.getName().trim().substring(0, 1);
        String value = spec.getValue();
        return String.format("%s%s", name, value);
    }
    
    private String standardizeCode(String code, int length) {
        if (code.length() < length) {
            return String.format("%" + length + "s", code).replace(' ', '0');
        } else if (code.length() > length) {
            return code.substring(0, length);
        } else {
            return code;
        }
    }
}
