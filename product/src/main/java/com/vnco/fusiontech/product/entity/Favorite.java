package com.vnco.fusiontech.product.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.product.entity.proxy.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@SuppressWarnings("serial")
@Accessors(chain = true)
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = DBConstant.FAVORITE_TABLE)
public class Favorite implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    private Product product;

}
