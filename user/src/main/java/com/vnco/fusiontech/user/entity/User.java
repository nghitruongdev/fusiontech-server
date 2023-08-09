package com.vnco.fusiontech.user.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import com.vnco.fusiontech.common.utils.FirebaseUtils;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;
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
//@Where(clause = "is_disabled=false")
public class User {
    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firebase_uid", unique = true)
    private String firebaseUid;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "is_staff")
    private boolean isStaff;

    @Column(name = "is_disabled")
    private boolean isDisabled;
    
    @Column(name = "image")
    private String image;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private Gender gender;
    
    @Column(name = "is_verified")
    private Boolean isVerified;

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
