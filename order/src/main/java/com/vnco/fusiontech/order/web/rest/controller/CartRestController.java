package com.vnco.fusiontech.order.web.rest.controller;

import com.vnco.fusiontech.order.service.OrderService;
import com.vnco.fusiontech.order.web.rest.request.CreateOrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/cart")
public class CartRestController {
    
    private final OrderService service;
    
    @PostMapping("/checkout")
    @ResponseStatus (HttpStatus.CREATED)
    public ResponseEntity<?> checkout(@Valid  @RequestBody CreateOrderRequest request) {
        var id = service.createOrder(request);
        return ResponseEntity.ok(id);
    }
    
}
