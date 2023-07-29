package com.vnco.fusiontech.product.entity.projection;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Category;
import com.vnco.fusiontech.product.entity.Product;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = Product.PROJECTION.BASIC, types = { Product.class })
public interface ProductBasic {

  Long getId();

  String getName();

  String getSlug();

  String getSummary();

  String getDescription();

  Object getImages();

  Integer getReviewCount();

  Integer getAvgRating();

  Object getFeatures();

  @JsonIncludeProperties("id")
  Brand getBrand();

  @JsonIncludeProperties("id")
  Category getCategory();
}
