package com.vnco.fusiontech.order.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.common.constant.PaymentMethod;
import com.vnco.fusiontech.common.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount")
    private Double        amount;
    @Column(name = "paid_at")
    private LocalDateTime paidAt;
    
    @Enumerated (EnumType.STRING)
//    @Column(name = "status", nullable = false)
    @JoinColumn(name = "status")
    private PaymentStatus status;
    
    @Enumerated (EnumType.STRING)
//    @Column(nullable = false)
    @JoinColumn(name = "method")
    private PaymentMethod method;
    
    @OneToOne (mappedBy = "payment", optional = false)
    @ToString.Exclude
    private Order order;
    
}
