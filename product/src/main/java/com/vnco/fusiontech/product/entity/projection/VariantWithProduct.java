package com.vnco.fusiontech.product.entity.projection;

import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Variant;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = Variant.PROJECTION.PRODUCT, types = Variant.class)
public interface VariantWithProduct {

        Product getProduct();

        int getAvailableQuantity();

        Long getId();

        String getSku();

        Object getImages();

        double getPrice();
}
