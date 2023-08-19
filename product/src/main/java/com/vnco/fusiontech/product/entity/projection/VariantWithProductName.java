package com.vnco.fusiontech.product.entity.projection;

import org.springframework.data.rest.core.config.Projection;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Variant;

@Projection(name = Variant.PROJECTION.PRODUCT_NAME, types = { Variant.class })
public interface VariantWithProductName extends VariantWithProduct {

  @JsonIncludeProperties({ "id", "name", "images" })
  Product getProduct();
}
