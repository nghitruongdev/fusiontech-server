package com.vnco.fusiontech.product.entity.proxy;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
@Getter
@NoArgsConstructor
@Entity(name = "ProductUser")
@Table(name = DBConstant.USER_TABLE)
public class User implements Serializable {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long   id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "image")
    private String image;
    
    public User(Long id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
    
        return Objects.equals(id, user.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
