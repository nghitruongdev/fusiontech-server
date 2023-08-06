package com.vnco.fusiontech.product.entity;
import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.product.entity.proxy.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@SuppressWarnings("serial")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DBConstant.REVIEW_TABLE)
@ToString
public class Review implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    private String comment;

    private Instant createdAt;

    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    private Product product;










}
