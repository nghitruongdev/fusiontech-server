package com.vnco.fusiontech.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    @Column(name = "code", unique = true)
    String code;

    @Positive
    Double discount;

    String description;
    @Positive
    Double minOrderAmount;
    @Positive
    Double maxDiscountAmount;

    Instant startDate;

    Instant expirationDate;
    Boolean active;

}
