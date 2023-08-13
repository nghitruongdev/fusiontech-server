package com.vnco.fusiontech.product.entity.projection;

import com.vnco.fusiontech.product.entity.Product;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "only-name", types = {Product.class})
public interface ProductOnlyName {
    
    Long getId();
    String getName();
}
