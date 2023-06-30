package com.vnco.fusiontech.product.entity;

import com.vnco.fusiontech.common.AbstractAuditingEntity;
import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table (name = DBConstant.VARIANT_INVENTORY_TABLE)
public class VariantInventory extends AbstractAuditingEntity<Long> {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Formula (
            "(SELECT COALESCE(SUM(s.quantity), 0) FROM " +
            DBConstant.VARIANT_INVENTORY_DETAIL_TABLE +
            " s WHERE s.inventory_id=id)"
    )
    private Integer totalQuantity;

    @Formula (
            "(SELECT COALESCE(SUM(s.quantity * s.price), 0) FROM " +
            DBConstant.VARIANT_INVENTORY_DETAIL_TABLE +
            " s WHERE s.inventory_id=id)"
    )
    private Double totalAmount;
    
    @NotEmpty
    @OneToMany (mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    List<VariantInventoryDetail> items = new ArrayList<>();
    
    @PrePersist
    public void prePersist() {
        if (items != null) {
            items.forEach(item -> item.setInventory(this));
        }
    }
    
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
