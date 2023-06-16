package com.vnco.fusiontech.user.repository;

import com.vnco.fusiontech.user.entity.Authority;
import com.vnco.fusiontech.user.entity.Role;
import com.vnco.fusiontech.user.entity.User;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    List<Authority> findByRole(Role role);
    Authority findByUserId(Long userId);
    Authority findByUserAndRole(Long userid, Role role);
    Authority findByUserIdAndRoleName(UUID userId, String role);
    Authority findByUser(@NonNull User userId);
    boolean existsAuthorityByUser_Id(Long userId);

}

