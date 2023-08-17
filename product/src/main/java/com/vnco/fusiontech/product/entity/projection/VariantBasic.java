package com.vnco.fusiontech.product.entity.projection;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Variant;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = Variant.PROJECTION.BASIC, types = Variant.class)
public interface VariantBasic {
  Long getId();

  String getSku();

  Object getImages();

  Double getPrice();

  @JsonIncludeProperties({ "id" })
  Product getProduct();

  Integer getAvailableQuantity();

  Long getSoldCount();

  Boolean getActive();
}
