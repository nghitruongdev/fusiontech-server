package com.vnco.fusiontech.order.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Accessors (chain = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table (name = DBConstant.PAYMENT_TABLE)
public class Payment implements Serializable {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double amount;
    
    private Instant       paidAt;
    
    @Enumerated (EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;
    
    @Enumerated (EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;
    
    @OneToOne (mappedBy = "payment", optional = false)
    @ToString.Exclude
    private Order order;
    
}
