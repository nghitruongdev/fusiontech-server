package com.vnco.fusiontech.product.entity.projection;

import java.util.List;

import org.springframework.data.rest.core.config.Projection;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Variant;

@Projection(name = Variant.PROJECTION.BASIC, types = Variant.class)
public interface VariantBasic {
  Long getId();

  String getSku();

  List<String> getImages();

  double getPrice();

  @JsonIncludeProperties({ "id" })
  Product getProduct();

  int getAvailableQuantity();
}
