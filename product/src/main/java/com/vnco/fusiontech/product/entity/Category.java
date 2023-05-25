package com.vnco.fusiontech.product.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.Data;

import java.io.Serializable;
@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    // Constructors, getters, setters, and other methods
}
=======
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "parten_id")
    private Integer partenId;
//    @OneToMany(mappedBy = "categoryByCategoryId")
//    private Collection<Product> productsById;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id && Objects.equals(name, category.name) && Objects.equals(partenId, category.partenId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, partenId);
    }

}
>>>>>>> 0f50ac4 (done API category vs brand)
