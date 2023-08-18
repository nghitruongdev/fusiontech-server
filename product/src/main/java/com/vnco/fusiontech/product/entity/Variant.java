package com.vnco.fusiontech.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vnco.fusiontech.common.constant.DBConstant;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.*;

@Accessors(chain = true)
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = DBConstant.PRODUCT_VARIANT_TABLE)
public class Variant implements Serializable {
    public interface PROJECTION {
        String PRODUCT = "product";
        String WITH_SPECS = "specifications";
        String PRODUCT_NAME = "product-name";
        String BASIC = "basic";
    }

    private interface FORMULA {
        String AVAILABLE_QUANTITY = "(SELECT get_available_quantity(id))";
        String SOLD_COUNT = "(SELECT get_sold_count(id))";
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku")
//    @NaturalId(mutable = true)
    private String sku;

    @Type(JsonType.class)
    @Column(name = "images", columnDefinition = "json")
    @Builder.Default
    @ToString.Exclude
    private List<String> images = new ArrayList<>();

    @Column(name = "price")
    private Double price;

    @Formula(FORMULA.AVAILABLE_QUANTITY)
    private Integer availableQuantity;

    @Formula(FORMULA.SOLD_COUNT)
    private Long soldCount;

    @Column(name = "active")
    @Builder.Default
    private Boolean active = Boolean.TRUE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "variant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @JsonIgnore
    private List<VariantInventoryDetail> inventories = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinTable(name = DBConstant.VARIANT_SPEC_TABLE, joinColumns = @JoinColumn(name = "variant_id"), inverseJoinColumns = @JoinColumn(name = "specification_id"))
    @Builder.Default
    @ToString.Exclude
    @JsonIgnore
    private Set<Specification> specifications = new HashSet<>();

    public void addSpecification(Specification spec) {
        specifications.add(spec);
        spec.getVariants().add(this);
    }

    public void removeSpecification(Specification spec) {
        specifications.remove(spec);
        spec.getVariants().remove(this);
    }

    public void setSpecification(List<Specification> specifications){

        if(this.specifications != null) {
            var list = new ArrayList<Specification>(this.specifications);
            for (int i = 0; i < this.specifications.size(); i++) {
                removeSpecification(list.get(i));
            }
            this.specifications.forEach(this::removeSpecification);
        }
        specifications.forEach(this::addSpecification);
    }
    public void addInventory(VariantInventoryDetail inventory) {
        inventory.setVariant(this);
        inventories.add(inventory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Variant variant = (Variant) o;

        return Objects.equals(id, variant.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
