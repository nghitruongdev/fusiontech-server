package com.vnco.fusiontech.order.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = DBConstant.VOUCHER_TABLE)
public class Voucher {
    private interface FORMULA {
        String VOUCHER_USAGE = """
                               SELECT COALESCE(COUNT(o.VOUCHER_ID), 0) AS usage
                               FROM APP_ORDER o JOIN VOUCHER V on o.VOUCHER_ID = V.ID
                               WHERE V.CODE =code
                               """;
    }
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;
    
    @Column (name = "code", nullable = false, unique = true)
    private String code;
    
    @Column (name = "description")
    private String description;
    
    @Column(name = "discount")
    private Byte discount;
    
    @Column(name = "min_order_amount")
    private Double minOrderAmount;
    
    @Column(name = "max_discount_amount")
    private Double maxDiscountAmount;
    
    @Column(name = "start_date")
    private LocalDateTime startDate;
    
    @Column (name = "expiration_date")
    private LocalDateTime expirationDate;
    
    @Column (name = "limit_usage")
    private Integer limitUsage;
    
    @Column (name = "user_limit_usage")
    private Short userLimitUsage;
    
//    @Formula (FORMULA.VOUCHER_USAGE)
//    private Integer usage;
//
//    private Double getUsagePercent(){
//        return usage != null ? ( usage.doubleValue() / limitUsage) * 100 : 0;
//    }
}
