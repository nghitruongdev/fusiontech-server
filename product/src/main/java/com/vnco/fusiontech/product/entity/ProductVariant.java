package com.vnco.fusiontech.product.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "productvariant")
public class ProductVariant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double price;

    private int available_quantity;

    private int stock_quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

}
