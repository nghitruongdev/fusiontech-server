package com.vnco.fusiontech.product.entity.projection;

import com.vnco.fusiontech.product.entity.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "name-and-variant-count", types = {Product.class})
public interface ProductOnlyName {

    Long getId();
    String getName();
    @Value("#{target.variants.size()}")
    Integer getVariantCount();
}
