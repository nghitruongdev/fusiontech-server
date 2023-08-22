package com.vnco.fusiontech.product.entity.projection;

import com.vnco.fusiontech.product.entity.Product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "name-and-variant-count", types = { Product.class })
public interface ProductNameAndVariantCount {

    Long getId();

    String getName();

    Boolean getActive();

    @Value("#{target.variants.size()}")
    Integer getVariantCount();

}
