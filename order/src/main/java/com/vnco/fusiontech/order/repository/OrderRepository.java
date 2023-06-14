package com.vnco.fusiontech.order.repository;

import com.vnco.fusiontech.common.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
