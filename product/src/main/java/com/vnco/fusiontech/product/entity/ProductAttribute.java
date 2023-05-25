package com.vnco.fusiontech.product.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "productvariant")
public class ProductAttribute implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    ProductVariant variant;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    Attribute attribute;

    private String value;
}
