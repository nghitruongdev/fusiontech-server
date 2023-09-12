package com.vnco.fusiontech.order.entity.projection;

import com.vnco.fusiontech.common.constant.OrderStatus;
import com.vnco.fusiontech.order.entity.Order;
import com.vnco.fusiontech.order.entity.Payment;
import com.vnco.fusiontech.order.entity.UserOrder;
import com.vnco.fusiontech.order.entity.Voucher;
import lombok.Getter;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(name = "with-payment", types = {Order.class})
public interface OrderWithPayment {
    Long getId();
    String getNote();
    String getEmail();
    LocalDateTime getPurchasedAt();
    OrderStatus getStatus();
    Long getUserId();
    UserOrder getUser();
    Voucher getVoucher();
    Long getAddressId();
    Long getPaymentId();
    Payment getPayment();
//    private Set<OrderItem> items = new HashSet<>();
}
