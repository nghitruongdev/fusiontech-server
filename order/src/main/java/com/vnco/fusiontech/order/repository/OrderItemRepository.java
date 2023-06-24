package com.vnco.fusiontech.order.repository;

import com.vnco.fusiontech.order.entity.OrderItem;
import com.vnco.fusiontech.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RepositoryRestResource
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    @Query (
            """
            SELECT COALESCE (SUM (oi.quantity), 0)
            FROM OrderItem oi
            WHERE oi.variant.id=:variantId
                AND oi.order.status IN :soldStatus
            """
    )
    long getSoldCountOfVariant(Long variantId, List<OrderStatus> soldStatus);
    
    
    @Query (
            """
            SELECT COALESCE(SUM(vi.quantity), 0)
            FROM VariantInventory vi
            WHERE vi.variant.id=:variantId
            """
    )
    long getTotalQuantityOfVariant(Long variantId);
    
}
