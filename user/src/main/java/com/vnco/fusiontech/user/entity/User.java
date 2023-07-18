package com.vnco.fusiontech.user.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.common.utils.FirebaseUtils;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Objects;

@Accessors(chain = true)
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

    @Column(name = "firebase_uid", unique = true, nullable = false, updatable = false)
    private String firebaseUid;
    @Basic
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Basic
    @Column(name = "email", unique = true)
    private String email;
    @Basic
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    @Basic
    @Column(name = "photo_url")
    private String photoUrl;

    private Date dateOfBirth;

    private Boolean gender;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_address_id")
    @ToString.Exclude
    private ShippingAddress defaultAddress;

    public String getFullName() {
        return FirebaseUtils.composeFullName(firstName, lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        if (!Objects.equals(id, user.id))
            return false;
        return Objects.equals(firebaseUid, user.firebaseUid);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firebaseUid != null ? firebaseUid.hashCode() : 0);
        return result;
    }
}
