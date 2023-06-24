package com.vnco.fusiontech.product.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;


@SuppressWarnings("serial")
@Accessors(chain = true)
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = DBConstant.PRODUCT_ATTRIBUTE_TABLE)
public class ProductAttribute implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Column (name = "attribute_value")
    private String value;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    private ProductVariant variant;

}
