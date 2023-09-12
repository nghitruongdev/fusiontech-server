package com.vnco.fusiontech.order.web.rest.controller;

import com.vnco.fusiontech.common.constant.OrderStatus;
import com.vnco.fusiontech.common.constant.OrderStatusGroup;
import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.order.service.OrderService;
import com.vnco.fusiontech.order.web.rest.request.CreateOrderRequest;
import com.vnco.fusiontech.order.web.rest.request.UpdateStatusRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@RepositoryRestController
@Slf4j
@CrossOrigin("*")
public class OrderRestController {

    private final OrderService service;

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        throw new InvalidRequestException("Not found");
    }

    @PatchMapping(value = "/orders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long id, @Valid @NotNull @RequestBody UpdateStatusRequest req) {
        service.updateOrderStatus(id, req.status());
        return ResponseEntity.ok().build();

    }

    @GetMapping("/orders/statuses")
    public ResponseEntity<?> status(@RequestParam(value = "group", required = false) Optional<OrderStatusGroup> group
    ) {
        if (group.isPresent()) {
            var statuses =
                    OrderStatus.getStatusesByGroup(group.get()).stream().map(OrderStatus::getFullStatus).toList();
            return ResponseEntity.ok(statuses);
        }
        var list = Arrays.stream(OrderStatus.values()).map(OrderStatus::getFullStatus).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/orders/statuses/groups")
    public ResponseEntity<?> statusGroups() {
        var list = Arrays.stream(OrderStatusGroup.values()).map(OrderStatusGroup::getFullDetail).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/orders/count")
    public ResponseEntity<?> countOrders(@RequestParam(name = "status", defaultValue = "COMPLETED") String status) {
        OrderStatus os = OrderStatus.valueOf(status.toUpperCase());
        var ok = service.countOrderByStatus(os);
        return ResponseEntity.ok(ok);
    }
}
