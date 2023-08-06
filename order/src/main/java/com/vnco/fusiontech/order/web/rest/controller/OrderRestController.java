package com.vnco.fusiontech.order.web.rest.controller;

import com.vnco.fusiontech.common.service.PublicVoucherService;
import com.vnco.fusiontech.order.entity.OrderStatus;
import com.vnco.fusiontech.order.entity.OrderStatusGroup;
import com.vnco.fusiontech.order.entity.proxy.Voucher;
import com.vnco.fusiontech.order.service.OrderService;
import com.vnco.fusiontech.order.web.rest.request.CreateOrderRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@RepositoryRestController
public class OrderRestController {
    
    private final OrderService service;
    private final PublicVoucherService voucherService;
    
    @PostMapping ("/orders")
    @ResponseStatus (HttpStatus.CREATED)
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        var id = service.createOrder(request);
        return ResponseEntity.ok(id);
    }
    
    @PatchMapping ("/orders/{id}")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateStatus(@PathVariable ("id") Long id, @Valid @NotNull @RequestBody OrderStatus status) {
        service.updateOrderStatus(id, status);
        return ResponseEntity.ok().build();
        
    }
    
    @GetMapping ("/orders/statuses")
    public ResponseEntity<?> status(@RequestParam (value = "group", required = false) Optional<OrderStatusGroup> group
    ) {
        if (group.isPresent()) {
            var statuses =
                    OrderStatus.getStatusesByGroup(group.get()).stream().map(OrderStatus::getFullStatus).toList();
            return ResponseEntity.ok(statuses);
        }
        var list = Arrays.stream(OrderStatus.values()).map(OrderStatus::getFullStatus).toList();
        return ResponseEntity.ok(list);
    }
    
    @GetMapping ("/orders/statuses/groups")
    public ResponseEntity<?> statusGroups() {
        var list = Arrays.stream(OrderStatusGroup.values()).map(OrderStatusGroup::getFullDetail).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/orders/vouchers/{code}")
    public ResponseEntity<?> checkVoucher(@PathVariable("code") String code) {
        String usage = service.checkVoucherUsage(code);
        return ResponseEntity.ok(usage);
    }
}
