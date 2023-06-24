package com.vnco.fusiontech.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.common.utils.BeanUtils;
import com.vnco.fusiontech.product.service.ProductVariantService;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@Accessors(chain = true)
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table (name = DBConstant.PRODUCT_VARIANT_TABLE)
public class ProductVariant implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String image;
    
    private double price;
    
    //    @Transient
    //    private long availableQuantity;
    //
    //    @Transient
    //    private long stockQuantity;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    private Product product;
    
    @OneToMany (mappedBy = "variant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @JsonIgnore
    private List<VariantInventory> inventories = new ArrayList<>();
    
    public void addInventory(VariantInventory inventory) {
        inventory.setVariant(this);
        inventories.add(inventory);
    }
    
    public long getAvailableQuantity() {
        var service = BeanUtils.getBean(ProductVariantService.class);
        return service.getAvailableQuantity(this.id);
    }
}
