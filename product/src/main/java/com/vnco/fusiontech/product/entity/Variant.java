package com.vnco.fusiontech.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.common.utils.BeanUtils;
import com.vnco.fusiontech.product.service.ProductVariantService;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        String WITH_ATTRIBUTE = "attributes";
        String PRODUCT_NAME = "product-name";
        String BASIC = "basic";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId(mutable = true)
    private String sku;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    @Builder.Default
    @ToString.Exclude
    private List<String> images = new ArrayList<>();

    private double price;

    // private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private Product product;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<VariantAttribute> attributes = new ArrayList<>();

    @OneToMany(mappedBy = "variant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @JsonIgnore
    private List<VariantInventoryDetail> inventories = new ArrayList<>();

    public void addAttribute(VariantAttribute attribute) {
        attribute.setVariant(this);
        attributes.add(attribute);
    }

    public void removeAttribute(VariantAttribute attribute) {
        attribute.setVariant(null);
        attributes.removeIf(attribute1 -> attribute1.getId().equals(attribute.getId()));
    }

    public void setAttributes(Collection<VariantAttribute> attributes) {
        this.attributes.forEach(attribute -> attribute.setVariant(null));
        this.attributes.clear();
        attributes.forEach(this::addAttribute);
    }

    public void addInventory(VariantInventoryDetail inventory) {
        inventory.setVariant(this);
        inventories.add(inventory);
    }

    public long getAvailableQuantity() {
        var service = BeanUtils.getBean(ProductVariantService.class);
        return service.getAvailableQuantity(this.id);
    }
}
