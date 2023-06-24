package com.vnco.fusiontech.product.entity.proxy;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("serial")
@Getter
@NoArgsConstructor
@Entity(name = "ProductUser")
@Table(name = DBConstant.USER_TABLE)
public class User implements Serializable {
    
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "id")
    private UUID   id;
    
    public User(UUID id) {
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
