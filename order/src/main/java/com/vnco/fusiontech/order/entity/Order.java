package com.vnco.fusiontech.order.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.common.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private Double total;
    
    private String note;
    
    private String email;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    @JoinColumn (name = "user_id", table = DBConstant.USER_TABLE)
    private UUID userId;
    
    @JoinColumn (name = "address_id", table = DBConstant.SHIPPING_ADDRESS_TABLE)
    private Long addressId;
    
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private Set<OrderItem> items = new HashSet<>();
    
    //TODO: add modification table mapping
   
    public void addOrderItem(OrderItem item){
        addItem(this, items,item );
    }
    
    public void removeOrderItem(OrderItem item){
        removeItem(this, items, item);
    }
    
    public void setOrderItems(Set<OrderItem> items){
        replace(this, this.items, items);
    }
    
}
