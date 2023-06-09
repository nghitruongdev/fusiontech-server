package com.vnco.fusiontech.user.service;


import com.vnco.fusiontech.user.entity.Role;

import java.util.Optional;

public interface RoleService {

    Role createRole(Role role);
    Optional<Role> getRole(String roleName);
    Role updateRole(String role);
    void deleteRole(Role roleName);
}
