package com.vnco.fusiontech.order.service.impl;

import com.vnco.fusiontech.common.constant.MailTemplate;
import com.vnco.fusiontech.common.constant.OrderStatus;
import com.vnco.fusiontech.common.constant.PaymentStatus;
import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.common.service.PublicMailService;
import com.vnco.fusiontech.common.service.PublicProductVariantService;
import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.common.web.request.mail.OrderRequest;
import com.vnco.fusiontech.order.entity.Order;
import com.vnco.fusiontech.order.entity.OrderItem;
import com.vnco.fusiontech.order.entity.UserOrder;
import com.vnco.fusiontech.order.exception.InsufficientQuantityException;
import com.vnco.fusiontech.order.mapper.OrderMapper;
import com.vnco.fusiontech.order.repository.OrderItemRepository;
import com.vnco.fusiontech.order.repository.OrderRepository;
import com.vnco.fusiontech.order.service.OrderService;
import com.vnco.fusiontech.order.web.rest.request.CreateOrderRequest;
import com.vnco.fusiontech.order.web.rest.request.OrderItemRequest;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository     repository;
    private final OrderItemRepository orderItemRepository;
    private final PublicUserService   userService;
    private final OrderMapper         mapper;
    private final PublicMailService   mailService;
    private final EntityManager       entityManager;
    private final PublicProductVariantService variantService;
    @Override
    public Long createOrder(CreateOrderRequest request) {
        if (!userService.existsById(request.userId())) {
            throw new RecordNotFoundException("No user was found with the given id: " + request.userId());
        }
        
        if (!userService.hasShippingAddress(request.userId(), request.addressId())) {
            throw new InvalidRequestException("No address was found with the given user");
        }
        
        checkEnoughQuantity(request.items());
        
        Order order = mapper.toOrder(request);
    
        order.setPurchasedAt(LocalDateTime.now());
    
        var saved = repository.save(order);
    
        clearCartItems(request.items());
        sendMailAfterOrderCheckoutSuccess(saved);
        return saved.getId();
    }
    
    private void throwError() {
        throw new InvalidRequestException("Hello there");
    }
    
    @Override
    public void updateOrderStatus(Long oid, @NonNull OrderStatus newStatus) {
        
        var order = repository.findById(oid).orElseThrow(RecordNotFoundException::new);
        
        checkUpdateOrder(order, newStatus);
        order.setStatus(newStatus);
        updatePayment(order);
    }
    
    private void checkUpdateOrder(Order order, OrderStatus newStatus){
        var status = order.getStatus();
        var payment = order.getPayment();
        if (status.isUnchangeable() || order.getStatus().compareTo(newStatus) >= 0) {
            throw new InvalidRequestException("Trạng thái đơn hàng mới không hợp lệ.");
        }
        switch(status){
            case CANCELLED -> {
                if (!status.isCancellable() || payment.getStatus() == PaymentStatus.COMPLETED) {
                    throw new InvalidRequestException(
                            "Không thể huỷ đơn hàng. Liên hệ nhân viên để được hỗ trợ");
                }
            }
            case DENIED -> {
                if (status.isUnchangeable()) {
                    throw new InvalidRequestException("Không thể thay đổi trạng thái đơn hàng. Chỉ có thể huỷ  " +
                                                          "đơn hàng mới không hợp lệ.");
                }
            }
            case COMPLETED -> {
                log.warn("Order with id {} that's not been delivered is going to be completed.", order.getId());
            }
        }
    }
    
    private void updatePayment(Order o){
        var payment = o.getPayment();
        switch(o.getStatus()){
//            case CANCELLED -> payment.setStatus(PaymentStatus.CANCELLED);
            case COMPLETED -> {
                if(payment.getStatus() == PaymentStatus.PENDING) payment.setStatus(PaymentStatus.COMPLETED);
            }
        }
    }
    
    private void checkEnoughQuantity(Collection<OrderItemRequest> items) {
        items.stream()
             .filter(item -> getAvailableQuantity(item.variantId()) < item.quantity())
             .findFirst()
             .ifPresent((item) -> {
                 throw new InsufficientQuantityException(item.variantId());
             });
    }
    
    private void clearCartItems(Collection<OrderItemRequest> items) {
        log.warn("Clear cart items is not implemented");
    }
    
    @Override
    @Transactional (readOnly = true)
    @Deprecated
    public long getAvailableQuantity(Long variantId) {
        return orderItemRepository.getTotalQuantityOfVariant(variantId)
               - orderItemRepository.getSoldCountOfVariant(variantId, OrderStatus.soldStatusList());
    }
    
    private void sendMailAfterOrderCheckoutSuccess(Order order) {
        var user = entityManager.find(UserOrder.class, order.getUserId());
        String mail = StringUtils.hasText(order.getEmail())
                      ? order.getEmail()
                             .trim()
                      : user.getEmail();
        //
        if (mail == null) return;
        var ids   = order.getItems().stream().map(OrderItem::getId).toList();
        var query = entityManager.createQuery("""
                                              SELECT phone, CONCAT(address, ', ', ward, ', ', district, ', ', province ) FROM ShippingAddress WHERE ID=:id
                                              """);
        query.setParameter("id", order.getAddressId());
        Object[] address = (Object[]) query.getSingleResult();
        var voucherCode = order.getVoucher()!=null? order.getVoucher().getCode(): null;
        var items = orderItemRepository.findOrderItemInfoIn(ids);
        var mailRequest =
                OrderRequest.builder()
                            .mail(mail)
                            .subject("Đặt hàng thành công")
                            .template(MailTemplate.ORDER_SUCCESS)
                            .orderId(order.getId())
                            .name(user.getFirstName())
                            .phone(address[0] + "")
                        .orderTotal(order.getPayment().getAmount())
                        .address(address[1] + "")
                        .paymentStatus(order.getPayment().getStatus().name())
                        .shipping(0d)
                        .voucher(voucherCode)
                            .items(items)
                            .build();
        mailService.sendMail(mailRequest);
    }
}
