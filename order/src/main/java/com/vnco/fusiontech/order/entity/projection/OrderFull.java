package com.vnco.fusiontech.order.entity.projection;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.rest.core.config.Projection;

import com.vnco.fusiontech.common.constant.OrderStatus;
import com.vnco.fusiontech.order.entity.Order;
import com.vnco.fusiontech.order.entity.OrderItem;
import com.vnco.fusiontech.order.entity.Payment;
import com.vnco.fusiontech.order.entity.UserOrder;
import com.vnco.fusiontech.order.entity.Voucher;

@Projection(name = "full", types = { Order.class })
public interface OrderFull {
  Long getId();

  String getNote();

  String getEmail();

  LocalDateTime getPurchasedAt();

  OrderStatus getStatus();

  UserOrder getUser();

  Long getUserId();

  Long getAddressId();

  Payment getPayment();

  Voucher getVoucher();

  Collection<OrderItemWithVariant> getItems();
}