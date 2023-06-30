package com.vnco.fusiontech.order.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.common.entity.ManyToOneRelation;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors (chain = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = DBConstant.ORDER_ITEM_TABLE)
public class OrderItem implements Serializable, ManyToOneRelation<Order> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int quantity;
    
    private double price;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "order_id", nullable = false)
    @ToString.Exclude
    private Order order;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "variant_id")
    @ToString.Exclude
    private ProductVariant variant;
    
    @Override
    public void set(Order e) {
        this.order = e;
    }
}
