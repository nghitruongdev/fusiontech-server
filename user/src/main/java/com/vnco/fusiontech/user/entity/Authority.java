package com.vnco.fusiontech.user.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Objects;

@SuppressWarnings("serial")
@Accessors(chain = true)
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DBConstant.AUTHORITY_TABLE)
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "role_name")
    private String roleName;

    public Authority(User userId, String roleName) {
        this.userId = userId;
        this.roleName = roleName;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority = (Authority) o;
        return id == authority.id && Objects.equals(userId, authority.userId) && Objects.equals(roleName, authority.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, roleName);
    }
}
