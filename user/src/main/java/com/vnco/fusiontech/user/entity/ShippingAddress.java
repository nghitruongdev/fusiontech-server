package com.vnco.fusiontech.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Accessors (chain = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table (name = DBConstant.SHIPPING_ADDRESS_TABLE)
@EntityListeners(ShippingAddressListener.class)
@JsonIgnoreProperties(allowSetters = true, value = {"isDefault", "default"})
public class ShippingAddress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String phone;
    
    private String address;
    
    private String ward;
    
    private String district;
    
    private String province;
    
    @Transient
    private boolean isDefault;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "user_id")
    @ToString.Exclude
    private User user;
    
    public ShippingAddress(Long id) {
        this.id = id;
    }
}