package com.vnco.fusiontech.product.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.Data;

import java.io.Serializable;
@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private String image;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    Brand brand;
=======

import java.util.Collection;
import java.util.Objects;


public class Product {

>>>>>>> 0f50ac4 (done API category vs brand)
}
