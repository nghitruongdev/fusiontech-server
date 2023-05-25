package com.vnco.fusiontech.product.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.Data;

import java.io.Serializable;
@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "brand")
public class Brand implements Serializable {
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
public class Brand {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
//    @OneToMany(mappedBy = "brandByBrandId")
//    private Collection<Product> productsById;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return id == brand.id && Objects.equals(name, brand.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
>>>>>>> 0f50ac4 (done API category vs brand)
