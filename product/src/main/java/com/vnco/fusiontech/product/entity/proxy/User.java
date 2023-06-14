package com.vnco.fusiontech.product.entity.proxy;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;

import java.io.Serializable;

@SuppressWarnings("serial")
@Entity(name = "ProductUser")
@Table(name = DBConstant.USER_TABLE)
public class User2 implements Serializable {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int   id;

    private String username;

    private String password_hash;

    private String email;

    private String phone;
}
