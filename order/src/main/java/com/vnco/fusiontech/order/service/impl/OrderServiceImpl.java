package com.vnco.fusiontech.order.service.impl;

import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.common.exception.NotAcceptedRequestException;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.common.service.PublicVoucherService;
import com.vnco.fusiontech.order.constant.VoucherConstant;
import com.vnco.fusiontech.order.entity.Order;
import com.vnco.fusiontech.order.entity.OrderStatus;
import com.vnco.fusiontech.order.entity.PaymentStatus;
import com.vnco.fusiontech.order.entity.proxy.Voucher;
import com.vnco.fusiontech.order.exception.InsufficientQuantityException;
import com.vnco.fusiontech.order.mapper.OrderMapper;
import com.vnco.fusiontech.order.repository.OrderItemRepository;
import com.vnco.fusiontech.order.repository.OrderRepository;
import com.vnco.fusiontech.order.service.OrderService;
import com.vnco.fusiontech.order.web.rest.request.CreateOrderRequest;
import com.vnco.fusiontech.order.web.rest.request.OrderItemRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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
    private final PublicVoucherService voucherService;

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

        order.setPurchasedAt(Instant.now());

        var saved = repository.save(order);

        clearCartItems(request.items());

        return saved.getId();
    }

    @Override
    public void updateOrderStatus(Long oid, @NonNull OrderStatus newStatus) {

        var order = repository.findById(oid).orElseThrow(RecordNotFoundException::new);

        if (newStatus == OrderStatus.CANCELLED) {
            cancelOrder(order);
            return;
        }

        if (order.getStatus().isUnchangeable() || order.getStatus().compareTo(newStatus) >= 0) {
            throw new NotAcceptedRequestException("Trạng thái đơn hàng mới không hợp lệ.");
        }

        order.setStatus(newStatus);
    }

    @Override
    public String checkVoucherUsage(String code) {
        var timeUsed = VoucherConstant.TIME_USED;
        String message = "Số lần sử dụng: ";
        var voucherId = voucherService.getVoucherId(code);
        Voucher voucher = new Voucher(voucherId);
        var voucherUsage = repository.countOrderByVoucher(voucher);

        if (voucherUsage >= timeUsed) {
            voucherService.setInactive(voucherId);
            return message + voucherUsage + ", đã hết hạn";
        }
        return message + voucherUsage;
    }

    private void cancelOrder(Order order) {

        if (!order.getStatus().isCancellable() || order.getPayment().getStatus() == PaymentStatus.DA_THANH_TOAN) {
            throw new NotAcceptedRequestException(
                    "Không thể huỷ đơn hàng. Liên hệ nhân viên để được hỗ trợ");
        }

        order.setStatus(OrderStatus.CANCELLED);
    }

    private void denyOrder(Order order) {
        if (order.getStatus() != OrderStatus.PLACED) {
            throw new NotAcceptedRequestException("Không thể thay đổi trạng thái đơn hàng. Trạng thái " +
                                                  "đơn hàng mới không hợp lệ.");
        }

        order.setStatus(OrderStatus.DENIED);
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
    public long getAvailableQuantity(Long variantId) {
        return orderItemRepository.getTotalQuantityOfVariant(variantId)
               - orderItemRepository.getSoldCountOfVariant(variantId, OrderStatus.soldStatusList());
    }
}
