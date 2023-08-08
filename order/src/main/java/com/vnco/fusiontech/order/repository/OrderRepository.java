package com.vnco.fusiontech.order.repository;

import com.vnco.fusiontech.order.entity.Order;
import com.vnco.fusiontech.common.constant.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {
    @RestResource (path = "byUserIdAndStatusIn")
    List<Order> findAllByUserIdAndStatusIn(@Param ("uid") UUID userId, @Param ("st") List<OrderStatus> statusList);
}
