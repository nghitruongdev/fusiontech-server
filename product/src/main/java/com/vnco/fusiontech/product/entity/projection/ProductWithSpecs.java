package com.vnco.fusiontech.product.entity.projection;

import com.vnco.fusiontech.product.entity.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "specifications", types = Product.class)
public interface ProductWithSpecs extends ProductBasic {

  @Value("#{@productServiceImpl.getProductSpecifications(target.id)}")
  Object getSpecifications();


}
