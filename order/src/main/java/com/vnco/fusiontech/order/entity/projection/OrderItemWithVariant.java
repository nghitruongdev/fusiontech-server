package com.vnco.fusiontech.order.entity.projection;

import org.springframework.data.rest.core.config.Projection;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.vnco.fusiontech.order.entity.OrderItem;
import com.vnco.fusiontech.order.entity.OrderVariant;

@Projection(name = "with-variant", types = { OrderItem.class })
public interface OrderItemWithVariant {
  Long getId();

  Integer getQuantity();

  Double getPrice();

  Byte getDiscount();

  @JsonIncludeProperties({ "id" })
  OrderVariant getVariant();
}
