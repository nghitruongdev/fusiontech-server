package com.vnco.fusiontech.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.vnco.fusiontech.common.entity.AbstractAuditingEntity;
import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.common.entity.AppUser;
import com.vnco.fusiontech.product.event.InventoryListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(InventoryListener.class)
@Table (name = DBConstant.VARIANT_INVENTORY_TABLE)
public class VariantInventory extends AbstractAuditingEntity<Long> {
    private interface FORMULA{
        String TOTAL_QUANTITY =  "(SELECT COALESCE(SUM(s.quantity), 0) FROM " +
                                 DBConstant.VARIANT_INVENTORY_DETAIL_TABLE +
                                 " s WHERE s.inventory_id=id)";
    }
    @Id
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Formula (FORMULA.TOTAL_QUANTITY)
    private Integer totalQuantity;

    @NotEmpty
    @OneToMany (mappedBy = "inventory", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
                                                   CascadeType.DETACH})
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
    @JsonIncludeProperties({"id", "firstName"})
    public AppUser getCreatedBy() {
        return super.getCreatedBy();
    }
    
    @Override
    @JsonIncludeProperties({"id", "firstName"})
    @JsonIgnore
    public AppUser getLastModifiedBy() {
        return super.getLastModifiedBy();
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
