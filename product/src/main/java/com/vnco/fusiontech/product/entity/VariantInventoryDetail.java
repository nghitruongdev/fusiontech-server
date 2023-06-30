package com.vnco.fusiontech.product.entity;

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
@Table (name = DBConstant.VARIANT_INVENTORY_DETAIL_TABLE)
public class VariantInventoryDetail {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Positive
    private Integer quantity;
    
    @Column(name = "variant_id",nullable = false)
    private Long variantId;
    
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_inventory_variant"), insertable = false, updatable = false)
    @ToString.Exclude
    private Variant variant;
    
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn(updatable = false)
    @ToString.Exclude
    private VariantInventory inventory;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VariantInventoryDetail detail)) return false;
        
        return Objects.equals(id, detail.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
