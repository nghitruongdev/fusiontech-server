package com.vnco.fusiontech.product.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
@SuppressWarnings("serial")
@Data
@Entity
@Table(name = DBConstant.CATEGORY_TABLE)
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int parent_id;

    // Constructors, getters, setters, and other methods
}
