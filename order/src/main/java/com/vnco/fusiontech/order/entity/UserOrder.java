package com.vnco.fusiontech.order.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = DBConstant.USER_TABLE)
@Getter
public class UserOrder {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
}
