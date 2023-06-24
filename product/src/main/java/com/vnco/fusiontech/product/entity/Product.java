package com.vnco.fusiontech.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.product.entity.proxy.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;
    
    private String image;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    private Category category;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @ToString.Exclude
    private Brand brand;
    
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
}
