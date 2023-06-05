package com.vnco.fusiontech.product.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = DBConstant.PRODUCT_VARIANT_TABLE)
public class ProductVariant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String image;

    private double price;

    private int available_quantity;

    private int stock_quantity;

    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    private Product product;

}
