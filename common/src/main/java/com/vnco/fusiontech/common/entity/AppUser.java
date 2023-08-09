package com.vnco.fusiontech.common.entity;

import com.vnco.fusiontech.common.constant.DBConstant;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = DBConstant.USER_TABLE)
public class AppUser implements UserDetails {
    @Id
    private Long id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "is_disabled")
    private boolean isDisabled;
    
    @Transient
    private String displayName;
    
    @Column(name = "is_verified")
    private boolean isVerified;
    
    @Transient
    @Setter
    private Collection<? extends GrantedAuthority> authorities;
    
    
    @Override
    public String getPassword() {
        return null;
    }
    
    @Override
    public String getUsername() {
        return null;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    
    @Override
    public boolean isEnabled() {
        return !isDisabled;
    }
}
