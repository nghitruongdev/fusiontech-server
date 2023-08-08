package com.vnco.fusiontech.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.common.constant.OrderStatus;
import com.vnco.fusiontech.order.entity.listener.OrderListener;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.vnco.fusiontech.common.utils.ManyToOneUtils.*;

@Accessors (chain = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table (name = DBConstant.ORDER_TABLE)
@EntityListeners (OrderListener.class)
@JsonIgnoreProperties (allowGetters = true, value = {"paymentId"})
public class Order implements Serializable {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "note")
    private String note;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "purchased_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime purchasedAt;
    
    @Column(name = "status")
    @Enumerated (EnumType.STRING)
    private OrderStatus status;
    
    @Column (name = "user_id" )
    private Long userId;
    
    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name ="user_id", updatable = false, insertable = false)
    private UserOrder user;
    
    @ManyToOne(cascade ={CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
    
    @JoinColumn (table = DBConstant.SHIPPING_ADDRESS_TABLE)
    @Column(name = "address_id")
    private Long addressId;
    
    @Column (name = "payment_id", insertable = false, updatable = false)
    private Long paymentId;
    
    @OneToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false, orphanRemoval = true)
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
