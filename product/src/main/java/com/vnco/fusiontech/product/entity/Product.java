package com.vnco.fusiontech.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.product.entity.proxy.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Formula;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("serial")
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
    public interface   PROJECTION {
            String FULL  = "full";
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String slug;
    
    private String shortDescription;
    
    private String description;
    
    private String thumbnail;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (
            foreignKey = @ForeignKey (
                    foreignKeyDefinition = "FOREIGN KEY (category_id) REFERENCES Category(id) ON DELETE SET NULL ON " +
                                           "UPDATE CASCADE"
            )
    )
    @ToString.Exclude
    private Category category;

    @Formula (
            "(SELECT COUNT(s.id) FROM " +
            DBConstant.REVIEW_TABLE +
            " s WHERE s.product_id=id)"
    )
    private Integer reviewCount;
    
    @Formula (
            "(SELECT  COALESCE(AVG(s.rating), 0) FROM " +
            DBConstant.REVIEW_TABLE +
            " s WHERE s.product_id=id)"
    )
    private Double avgRating;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    private Brand brand;
    
    @OneToMany (mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
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
    @JoinTable (
            name = DBConstant.FAVORITE_TABLE, joinColumns = {@JoinColumn (name = "product_id")}
            , inverseJoinColumns = {@JoinColumn (name = "user_id")}
    )
    @Builder.Default
    @ToString.Exclude
    @JsonIgnore
    private Set<User> favorites = new HashSet<>();
    
    public void addFavoriteUser(User user) {
        favorites.add(user);
    }
    
    public void removeFavoriteUser(UUID uid) {
        favorites.removeIf(user -> user.getId().equals(uid));
    }
    
    public boolean favoritesContains(UUID uid) {
        return favorites.contains(new User(uid));
    }

//    public List<Product> getFavoriteProductByUserId(UUID uid){return favorites.;}
}
