package com.vnco.fusiontech.order.repository;

import com.vnco.fusiontech.common.web.request.mail.OrderItemDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomOrderItemRepository {
    
    List<OrderItemDTO> findOrderItemInfoIn(@Param("ids") List<Long> ids);
}
