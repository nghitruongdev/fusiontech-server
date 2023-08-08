package com.vnco.fusiontech.order.repository;

import com.vnco.fusiontech.order.entity.OrderItem;
import com.vnco.fusiontech.common.constant.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RepositoryRestResource
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("""
            SELECT COALESCE (SUM (oi.quantity), 0)
            FROM OrderItem oi
            WHERE oi.variant.id=:variantId
                AND oi.order.status IN :soldStatus
            """)
    @Deprecated
    long getSoldCountOfVariant(@Param("variantId") Long variantId, @Param("soldStatus") List<OrderStatus> soldStatus);

    @Query("""
            SELECT COALESCE(SUM(vi.quantity), 0)
            FROM VariantInventoryDetail vi
            WHERE vi.variant.id=:variantId
            """)
    @Deprecated
    long getTotalQuantityOfVariant(@Param("variantId") Long variantId);

}
