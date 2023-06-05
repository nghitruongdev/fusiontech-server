package com.vnco.fusiontech.product.entity.proxy;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@SuppressWarnings("serial")
@Entity
@Table(name = DBConstant.USER_TABLE)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
