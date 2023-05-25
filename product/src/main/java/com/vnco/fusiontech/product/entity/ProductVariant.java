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
    @Column(name = "id")
    int id;

    @Column(name = "price")
    double price;

    @Column(name = "available_quantity")
    int available_quantity;

    @Column(name = "stock_quantity")
    int stock_quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

}
