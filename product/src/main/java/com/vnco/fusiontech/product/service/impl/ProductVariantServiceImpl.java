package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.common.service.PublicOrderService;
import com.vnco.fusiontech.common.web.response.VariantWithProductInfoDTO;
import com.vnco.fusiontech.product.entity.*;
import com.vnco.fusiontech.product.entity.projection.ProductSpecificationDTO;
import com.vnco.fusiontech.product.repository.ProductVariantRepository;
import com.vnco.fusiontech.product.service.ProductService;
import com.vnco.fusiontech.product.service.ProductVariantService;
import com.vnco.fusiontech.product.web.rest.request.VariantRequest;
import com.vnco.fusiontech.product.web.rest.request.mapper.VariantMapper;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    private final EntityManager em;

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
        var productSpecs = productService.getProductSpecifications(variant.getProduct().getId());
        productSpecs.stream()
                .filter(spec -> spec.values().size() == 1)
                .forEach(spec -> variant.addSpecification(spec.values().get(0)));

        if (variant.getSku() == null) {
            generateSku(List.of(variant), productSpecs);
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

    private void generateSku(@NotEmpty List<Variant> variants, List<ProductSpecificationDTO> specs) {
        StringBuilder skuBuilder = new StringBuilder();
        var names = specs.stream()
                .filter(item -> item.values().size() > 1)
                .map(ProductSpecificationDTO::name).toList();
        var productId = variants.get(0).getProduct().getId();
        var product = em.find(Product.class, productId);
        skuBuilder.append(productId);
        if (product.getCategory() != null)
            skuBuilder.append("-")
                    .append(em.find(Category.class, product.getCategory().getId()).getName().substring(0, 2));
        if (product.getBrand() != null)
            skuBuilder.append("-").append(em.find(Brand.class, product.getBrand().getId()).getName().substring(0, 2));
        if (product.getName() != null)
            skuBuilder.append("-").append(product.getName().substring(0, 2));

        Function<Variant, String> getSpecCode = variant -> variant.getSpecifications().stream()
                .filter(spec -> names.contains(spec.getName()))
                .map(this::convertSpecToSkuCode)
                .collect(Collectors.joining("-"));
        var sku = skuBuilder.toString();
        variants.forEach(variant -> {
            var specCode = getSpecCode.apply(variant);
            variant.setSku(String.format("%s%s", sku, StringUtils.hasText(specCode) ? "-" + specCode : ""));
        });
    }

    private String convertSpecToSkuCode(Specification spec) {
        String name = spec.getName().trim().substring(0, 1);
        String value = spec.getValue();
        return String.format("%s%s", name, value);
    }
    
    @Override
    public List<VariantWithProductInfoDTO> getVariantOrProductImages(List<Long> variantIds) {
      return  repository.findVariantsWithProductInfo(variantIds);
    }
}
