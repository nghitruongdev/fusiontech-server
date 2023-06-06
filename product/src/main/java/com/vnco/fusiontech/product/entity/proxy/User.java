package com.vnco.fusiontech.product.entity.proxy;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@SuppressWarnings("serial")
@Entity(name = "ProductUser")
@Table(name = DBConstant.USER_TABLE)
public class User implements Serializable {
    
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "id")
    private UUID   id;
}
