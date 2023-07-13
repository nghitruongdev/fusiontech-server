package com.vnco.fusiontech.user.service;

import com.vnco.fusiontech.user.entity.Authority;
import com.vnco.fusiontech.user.entity.Role;
import com.vnco.fusiontech.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorityService {
    void updateRoleReference(Role oldRoleName, Role newRoleName);
    List<Authority> getAllAuthorities();

    Optional<Authority> getAuthorityById(Long id);

    Authority createAuthority(Long user, String role);

    void deleteAuthority(Long authId);

    Authority updateUserRole(User userId, Role role);

}

