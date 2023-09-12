package com.vnco.fusiontech.order.repository;

import com.vnco.fusiontech.common.constant.OrderStatus;
import com.vnco.fusiontech.order.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    @RestResource (path = "byUserIdAndStatusIn")
    List<Order> findAllByUserIdAndStatusIn(@Param ("uid") Long userId, @Param ("st") List<OrderStatus> statusList);

    @RestResource(path = "find-by-status-in")
    List<Order> findByStatusIn(@Param("list") List<OrderStatus> statusList, Pageable pageable);

    Long countOrderByStatus(OrderStatus status);

    Boolean existsByVoucherCode(String voucher);
}
