package com.vnco.fusiontech.order.service.impl;

import com.vnco.fusiontech.common.constant.OrderStatus;
import com.vnco.fusiontech.common.exception.InvalidRequestException;
import com.vnco.fusiontech.order.entity.Order;
import com.vnco.fusiontech.common.exception.RecordNotFoundException;
import com.vnco.fusiontech.common.service.PublicProductVariantService;
import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.order.exception.InsufficientQuantityException;
import com.vnco.fusiontech.order.mapper.OrderMapper;
import com.vnco.fusiontech.order.repository.OrderItemRepository;
import com.vnco.fusiontech.order.repository.OrderRepository;
import com.vnco.fusiontech.order.service.OrderService;
import com.vnco.fusiontech.order.web.rest.request.CreateOrderRequest;
import com.vnco.fusiontech.order.web.rest.request.OrderItemRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PublicUserService         userService;
    private final PublicProductVariantService variantService;
    private final OrderMapper mapper;
    
    @Override
    public UUID createOrder(CreateOrderRequest request) {
       
//        todo: check user id if exists
        if(!userService.existsById(request.userId())){
            throw new RecordNotFoundException("No user was found with the given id: " + request.userId());
        }
    
        //todo: check whether user has this address
        if(!userService.hasShippingAddress(request.userId(), request.addressId())){
            throw new InvalidRequestException("No address was found with the given user");
        }
    
        //todo: check quantity of variant before make a checkout
        checkEnoughQuantity(request.items());
    
        //todo: map request to an order
        Order order = mapper.toOrder(request);
        
        //todo: set status for this order
        
        //todo: save order to the database
        var saved = orderRepository.save(order);
        
        //todo: change variant quantity after checking out successful
        
        //todo: remove items from user's cart
        clearCartItems(request.items());
        return saved.getId();
    }
    
    @Override
    public void updateOrderStatus(UUID oid, OrderStatus newStatus) {
    
    }
    
    @Override
    public void cancelOrder(UUID oid) {
    
    }
    
    private void checkEnoughQuantity(Collection<OrderItemRequest> items){
        items.forEach(item -> {
            if(!variantService.hasEnoughQuantity(item.variantId(), item.quantity())){
                throw new InsufficientQuantityException(item.variantId());
            }
        });
    }
    
    private void clearCartItems(Collection<OrderItemRequest> items){
        log.warn("Clear cart items is not implemented");
    }
    
 
    
}
