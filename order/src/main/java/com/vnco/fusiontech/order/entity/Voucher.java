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
@Table(name = DBConstant.VOUCHER_TABLE)
public class Voucher {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "min_order_amount")
    private Double minOrderAmount;
    
    @Column(name = "max_discount_amount")
    private Double maxDiscountAmount;
    
    @Column(name = "start_date")
    private LocalDateTime startDate;
    
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    
    @Column(name = "limit_usage")
    private Integer limitUsage;
    
    @Column(name = "user_limit_usage")
    private Short userLimitUsage;
    
}
