package com.vnco.fusiontech.order.repository;

import com.vnco.fusiontech.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
