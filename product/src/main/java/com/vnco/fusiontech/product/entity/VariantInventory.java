package com.vnco.fusiontech.product.entity;

import com.vnco.fusiontech.common.AbstractAuditingEntity;
import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Objects;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table (name = DBConstant.PRODUCT_INVENTORY_TABLE)
public class VariantInventory extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Positive
    private Double price;
    
    @NotNull
    @Positive
    private Integer quantity;
    
    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private ProductVariant variant;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VariantInventory inventory)) return false;
        
        return Objects.equals(id, inventory.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
