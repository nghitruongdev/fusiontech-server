package com.vnco.fusiontech.product.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = DBConstant.PRODUCT_TABLE)
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private String image;

    private int quantity;

    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    private Category category;

    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    private Brand brand;
}
