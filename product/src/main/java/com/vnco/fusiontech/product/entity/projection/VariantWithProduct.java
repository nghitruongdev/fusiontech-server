package com.vnco.fusiontech.product.entity.projection;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Variant;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = Variant.PROJECTION.PRODUCT, types = Variant.class)
public interface VariantWithProduct {

        Product getProduct();

        int getAvailableQuantity();

        Long getId();

        String getSku();

        List<String> getImages();

        double getPrice();
}
