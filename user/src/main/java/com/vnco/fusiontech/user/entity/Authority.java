package com.vnco.fusiontech.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonIgnoreProperties({"authorities"})
    @JsonIncludeProperties({"id","username"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_name")
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority = (Authority) o;
        return Objects.equals(user, authority.user) && Objects.equals(role, authority.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }
}
