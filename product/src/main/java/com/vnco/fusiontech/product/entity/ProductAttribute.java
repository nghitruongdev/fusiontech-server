package com.vnco.fusiontech.product.entity;

import jakarta.persistence.*;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "productvariant")
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    ProductVariant variantId;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    Attribute attribute;
}
