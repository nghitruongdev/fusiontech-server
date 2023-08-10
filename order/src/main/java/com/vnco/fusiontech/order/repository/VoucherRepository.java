package com.vnco.fusiontech.order.repository;

import com.vnco.fusiontech.order.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @RestResource (path = "by-code")
    Optional<Voucher> findByCodeIgnoreCase(@Param ("code") String code);
    
    @Query("SELECT COALESCE(COUNT(o.voucher), 0) FROM Order o WHERE o.voucher.code=:code AND o.userId=:userId")
    @RestResource (path = "user-usage")
    Integer countUserUsageByCode(@Param ("code") String code, @Param ("userId") Long userId);
    
    @Query (
            value = """
                    SELECT COALESCE(COUNT(o.VOUCHER_ID), 0) FROM APP_ORDER o JOIN VOUCHER V on o.VOUCHER_ID = V.ID
                    WHERE V.CODE =:code
                    """, nativeQuery = true
    )
    @RestResource(path = "usage")
    Long countVoucherUsageByCode(@Param ("code") String code);
    
}
