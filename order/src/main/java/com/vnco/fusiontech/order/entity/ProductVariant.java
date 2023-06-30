package com.vnco.fusiontech.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors (chain = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "productVariant")
@Table(name = DBConstant.PRODUCT_VARIANT_TABLE)
@JsonIgnoreProperties (value = {"hibernateLazyInitializer"})
public class ProductVariant implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
