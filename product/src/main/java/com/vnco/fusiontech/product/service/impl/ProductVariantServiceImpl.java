package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.common.service.PublicOrderService;
import com.vnco.fusiontech.common.web.response.VariantWithProductInfoDTO;
import com.vnco.fusiontech.product.entity.*;
import com.vnco.fusiontech.product.entity.projection.ProductSpecificationDTO;
import com.vnco.fusiontech.product.repository.ProductVariantRepository;
import com.vnco.fusiontech.product.service.ProductService;
import com.vnco.fusiontech.product.service.ProductVariantService;
import com.vnco.fusiontech.product.service.SpecificationService;
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
    
    private final EntityManager        em;
    private final SpecificationService specificationService;

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
        specificationService.persistSpecifications(variant.getSpecifications());
        return repository.save(variant);
    }
    
    //    @Override
    //    public Variant updateProductVariant(Variant productVariant) {
    //        return repository.save(productVariant);
    //    }
    
    @Override
    public void deleteProductVariant(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    @Transactional (readOnly = true)
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
        updateVariantMultiSpec(request.specifications(), variant);
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

    @Override
    public void generateSku(Product product) {
        var specs = productService.getProductSpecifications(product.getId());
        var variants = product.getVariants();
        generateSku(variants, product.getId(), specs);

    }

    private void generateSku(@NotEmpty List<Variant> variants,
                             Long productId,
                             List<ProductSpecificationDTO> specs) {
        StringBuilder skuBuilder = new StringBuilder();
        var names = specs.stream()
                .filter(item -> item.values().size() > 1)
                .map(ProductSpecificationDTO::name).toList();
        Product product = em.find(Product.class, productId);
        skuBuilder.append(productId);
        if (product.getCategory() != null)
            skuBuilder.append("-")
                    .append(subName(em.find(Category.class, product.getCategory().getId()).getName()));
        if (product.getBrand() != null)
            skuBuilder.append("-").append(subName(em.find(Brand.class, product.getBrand().getId()).getName()));
        if (product.getName() != null)
            skuBuilder.append("-").append(subName(product.getName()));

        Function<Variant, String> getSpecCode = variant -> variant.getSpecifications().stream()
                .filter(spec -> names.contains(spec.getName()))
                .map(this::convertSpecToSkuCode)
                .collect(Collectors.joining("-"));
        var sku = skuBuilder.toString();
        variants.forEach(variant -> {
            var specCode = getSpecCode.apply(variant);
            specCode = StringUtils.hasText(specCode) ? specCode : String.valueOf(product.getVariants().size());
            variant.setSku(String.format("%s%s", sku, StringUtils.hasText(specCode) ? "-" + specCode : ""));
        });
//        variants.forEach(variant -> {
//            var specCode = getSpecCode.apply(variant);
//            specCode = StringUtils.hasText(specCode) ? specCode : String.valueOf(product.getVariants().size());
//            String finalSku = String.format("%s%s", sku, StringUtils.hasText(specCode) ? "-" + specCode : "");
//            variant.setSku(finalSku);
//            System.out.println("Generated SKU: " + finalSku); // logging the generated SKU
//        });
    }

    private void generateSku(@NotEmpty List<Variant> variants,
                             List<ProductSpecificationDTO> specs) {
        StringBuilder skuBuilder = new StringBuilder();
        var names = specs.stream()
                .filter(item -> item.values().size() > 1)
                .map(ProductSpecificationDTO::name).toList();
        var productId = variants.get(0).getProduct().getId();
        var product = em.find(Product.class, productId);
        skuBuilder.append(productId);
        if (product.getCategory() != null)
            skuBuilder.append("-")
                    .append(subName(em.find(Category.class, product.getCategory().getId()).getName()));
        if (product.getBrand() != null)
            skuBuilder.append("-").append(subName(em.find(Brand.class, product.getBrand().getId()).getName()));
        if (product.getName() != null)
            skuBuilder.append("-").append(subName(product.getName()));

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
        String name = subName(spec.getName());
        String value = subName(spec.getValue());
        return String.format("%s%s", name, value);
    }


    @Override
    public List<VariantWithProductInfoDTO> getVariantOrProductImages(List<Long> variantIds) {
        return repository.findVariantsWithProductInfo(variantIds);
    }

    private String subName(String name) {
        if (StringUtils.hasText(name)) {
            var clean = name.trim();
            return clean.substring(0, Math.min(clean.length(), 3)).trim().toUpperCase();
        }
        return name;
    }

    private void updateVariantMultiSpec(List<Specification> specifications, Variant variant) {
        if (specifications == null || specifications.isEmpty()) return;
        specificationService.persistSpecifications(specifications);
        var names   = specifications.stream().map(Specification::getName).toList();
        var removed = variant.getSpecifications().stream().filter(item -> names.contains(item.getName())).toList();
        removed.forEach(variant::removeSpecification);
        specifications.forEach(variant::addSpecification);
        generateSku(List.of(variant));
    }
}
