package com.vnco.fusiontech.user.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Accessors (chain = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = DBConstant.USER_TABLE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firebase_uid", unique = true, nullable = true)
    private String firebaseUid;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "phone_number")
    private String phoneNumber;
    @Basic
    @Column(name = "photo_url")
    private String photoUrl;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "default_address_id")
    @ToString.Exclude
    private ShippingAddress defaultAddress;

    @OneToMany(mappedBy = "user")
    private Set<Authority> authorities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firebaseUid, user.firebaseUid) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(photoUrl, user.photoUrl) && Objects.equals(defaultAddress, user.defaultAddress) && Objects.equals(authorities, user.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firebaseUid, name, email, phoneNumber, photoUrl, defaultAddress, authorities);
    }
}
