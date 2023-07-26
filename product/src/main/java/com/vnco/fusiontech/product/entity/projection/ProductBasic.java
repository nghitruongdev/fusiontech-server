package com.vnco.fusiontech.product.entity.projection;

import org.springframework.data.rest.core.config.Projection;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.vnco.fusiontech.common.entity.FirebaseImage;
import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Category;
import com.vnco.fusiontech.product.entity.Product;

@Projection(name = Product.PROJECTION.BASIC, types = { Product.class })
public interface ProductBasic {

  Long getId();

  String getName();

  String getSlug();

  String getSummary();

  String getDescription();

  FirebaseImage getThumbnail();

  Integer getReviewCount();

  Integer getAvgRating();

  Object getFeatures();

  @JsonIncludeProperties("id")
  Brand getBrand();

  @JsonIncludeProperties("id")
  Category getCategory();
}
