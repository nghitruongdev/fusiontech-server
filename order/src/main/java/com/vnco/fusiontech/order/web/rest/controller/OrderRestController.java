package com.vnco.fusiontech.order.web.rest.controller;

import com.vnco.fusiontech.common.constant.OrderStatus;
import com.vnco.fusiontech.order.service.OrderService;
import com.vnco.fusiontech.order.web.rest.request.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@RepositoryRestController
public class OrderRestController {
    
    private final OrderService service;
    
    @PostMapping ("/orders")
    @ResponseStatus (HttpStatus.CREATED)
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
        var id = service.createOrder(request);
        return ResponseEntity.ok(id);
    }
    
    @GetMapping("/orders/status")
    public ResponseEntity<OrderStatus[]> status(){
        return ResponseEntity.ok(OrderStatus.values());
    }
}
