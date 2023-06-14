package com.vnco.fusiontech.product.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
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
@Table (name = DBConstant.ATTRIBUTE_TABLE)
public class Attribute implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String name;
}
