package com.vnco.fusiontech.product.entity.projection;


import com.vnco.fusiontech.product.entity.Product;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "dynamic", types = {Product.class})
public interface ProductDynamic {
    Double getDiscount();
    String getStatus();
    Boolean getActive();
    Double getMinPrice();
    Double getMaxPrice();
}


