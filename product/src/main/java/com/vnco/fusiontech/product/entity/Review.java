package com.vnco.fusiontech.product.entity;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.product.entity.proxy.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    @Column(name = "rating")
    private int rating;

    @Column(name="comment")
    private String        comment;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    @JsonIncludeProperties({"id"})
    private User user;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    @JsonIncludeProperties({"id"})
    private Product product;










}
