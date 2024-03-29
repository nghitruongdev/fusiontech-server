package com.vnco.fusiontech.product.entity.projection;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Category;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Variant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = Product.PROJECTION.FULL, types = { Product.class })
public interface ProductFullDetails {

    Long getId();

    String getName();

    String getSlug();

    String getSummary();

    String getDescription();

    Object getImages();

    Integer getReviewCount();

    Integer getAvgRating();

    Object getFeatures();

    Double getMinPrice();

    Double getMaxPrice();

    Byte getDiscount();

    Boolean getActive();

    @JsonIncludeProperties({ "id", "name" })
    Brand getBrand();

    @JsonIncludeProperties("id")
    Category getCategory();

    @JsonIncludeProperties({ "id", "price" })
    List<Variant> getVariants();

    @Value("#{@productServiceImpl.getProductSpecifications(target.id)}")
    Object getSpecifications();


}
