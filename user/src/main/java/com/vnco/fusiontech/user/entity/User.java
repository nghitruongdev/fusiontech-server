package com.vnco.fusiontech.user.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = DBConstant.USER_TABLE)
public class User {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "id")
    private UUID   id;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password_hash")
    private String passwordHash;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "phone")
    private String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(passwordHash, user.passwordHash) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, passwordHash, email, phone);
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "authority", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_name", referencedColumnName = "role_name") })
    @BatchSize(size = 20)
    @Builder.Default
    @ToString.Exclude
    private Set<Role> authorities = new HashSet<>();

    boolean hasAuthority(Role role) {
        return authorities.contains(role);
    }

    public User addAuthority(Role role) {
        authorities.add(role);
        return this;
    }

    public User removeAuthority(String aName) {
        authorities.removeIf(auth -> auth.getName().equalsIgnoreCase(aName));
        return this;
    }


    /**
     * Get granted authorities
     * @return List of {@link SimpleGrantedAuthority} object that contains role name.
     *
     */
    public List<? extends GrantedAuthority> getGrantedAuthorities() {
        return authorities
                .stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
