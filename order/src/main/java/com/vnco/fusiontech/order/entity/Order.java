package com.vnco.fusiontech.order.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import static com.vnco.fusiontech.common.utils.ManyToOneUtils.*;

@Accessors(chain = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = DBConstant.ORDER_TABLE)
@EntityListeners(OrderListener.class)
public class Order implements Serializable {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double total;
    
    private String note;
    
    private String email;
    
    private Instant purchasedAt;
    
    @Enumerated (EnumType.STRING)
    //    @Column (nullable = false)
    @NotNull
    private OrderStatus status;
    
    @JoinColumn (name = "user_id", table = DBConstant.USER_TABLE)
    private UUID userId;
    
    @JoinColumn (name = "address_id", table = DBConstant.SHIPPING_ADDRESS_TABLE)
    private Long addressId;
    
    @OneToOne (cascade = CascadeType.PERSIST, optional = false, orphanRemoval = true)
    @JoinColumn (name = "payment_id")
    @ToString.Exclude
    private Payment payment;
    
    public void setPayment(@NonNull Payment payment) {
        payment.setOrder(this);
        this.payment = payment;
    }
    
    @OneToMany (mappedBy = "order", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private Set<OrderItem> items = new HashSet<>();
    
    //TODO: add modification table mapping
    
    public void addOrderItem(OrderItem item) {
        addItem(this, items, item);
    }
    
    public void removeOrderItem(OrderItem item) {
        removeItem(this, items, item);
    }
    
    public void setOrderItems(Set<OrderItem> items) {
        this.items = replace(this, this.items, items);
    }
    
}
