package com.vnco.fusiontech.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.product.entity.proxy.User;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.springframework.hateoas.RepresentationModel;

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
@Table(name = DBConstant.PRODUCT_TABLE)
public class Product extends RepresentationModel<Product> implements Serializable {
    public interface PROJECTION {
        String FULL = "full";
        String BASIC = "basic";
    }

    public interface FORMULA {
        String MIN_PRICE = "(SELECT MIN(v.price) FROM " +
                DBConstant.PRODUCT_VARIANT_TABLE + " v WHERE v.product_id=id AND v.active = TRUE)";
        String MAX_PRICE = "(SELECT MAX(v.price) FROM " +
                DBConstant.PRODUCT_VARIANT_TABLE + " v WHERE v.product_id=id AND v.active = TRUE)";

    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "slug")
    private String slug;
    @Column(name = "summary")
    private String summary;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Type(JsonType.class)
    @Column(name = "images", columnDefinition = "json")
    @Builder.Default
    private List<String> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(foreignKeyDefinition = """
            FOREIGN KEY (category_id) REFERENCES Category(id) ON DELETE SET NULL ON UPDATE CASCADE
            """))
    @ToString.Exclude
    private Category category;

    @JdbcTypeCode(SqlTypes.TINYINT)
    @Min(0)
    @Max(100)
    @Column(name = "discount")
    private Byte discount;

    @Formula("(SELECT COUNT(s.id) FROM " +
            DBConstant.REVIEW_TABLE +
            " s WHERE s.product_id=id)")
    private Integer reviewCount;

    @Formula("(SELECT COALESCE(AVG(s.rating), 0) FROM " +
            DBConstant.REVIEW_TABLE +
            " s WHERE s.product_id=id)")
    private Double avgRating;

    @Column(name = "status")
    private String status;

    @Column(name = "active")
    @Builder.Default
    private Boolean active = Boolean.TRUE;

    @Formula(value = FORMULA.MIN_PRICE)
    private Double minPrice;

    @Formula(value = FORMULA.MAX_PRICE)
    private Double maxPrice;

    @Type(JsonType.class)
    @Column(name = "features", columnDefinition = "json")
    @Builder.Default
    private List<String> features = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(foreignKeyDefinition = """
            FOREIGN KEY (brand_id)
            REFERENCES Brand(id) ON DELETE SET NULL ON UPDATE CASCADE
            """))
    @ToString.Exclude
    private Brand brand;

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    @ToString.Exclude
    private List<Variant> variants = new ArrayList<>();

    public void addVariant(Variant variant) {
        variant.setProduct(this);
        variants.add(variant);
    }

    public void removeVariant(Variant variant) {
        variant.setProduct(null);
        variants.removeIf(v -> v.getId().equals(variant.getId()));
    }

    public void setVariants(Collection<Variant> variants) {
        this.variants.forEach(variant -> variant.setProduct(null));
        this.variants.clear();
        variants.forEach(variant -> {
            variant.setProduct(this);
            this.variants.add(variant);
        });
    }

    @ManyToMany
    @JoinTable(name = DBConstant.FAVORITE_TABLE, joinColumns = {
            @JoinColumn(name = "product_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
    @Builder.Default
    @ToString.Exclude
    @JsonIgnore
    private Set<User> favorites = new HashSet<>();

    public void addFavoriteUser(User user) {
        favorites.add(user);
    }

    public void removeFavoriteUser(Long uid) {
        favorites.removeIf(user -> user.getId().equals(uid));
    }

    public boolean favoritesContains(Long uid) {
        return favorites.contains(new User(uid));
    }

    public Integer getVariantCount() {
        return variants.size();
    }

}
