package com.vnco.fusiontech.product.entity.projection;

import java.util.List;

import org.springframework.data.rest.core.config.Projection;

import com.vnco.fusiontech.product.entity.Product;

@Projection(name = "name-with-variants", types = { Product.class })
public interface ProductNameWithVariants {
  Long getId();

  String getName();

  Boolean getActive();

  List<VariantBasic> getVariants();
}
