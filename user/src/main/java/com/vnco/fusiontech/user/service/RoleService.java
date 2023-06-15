package com.vnco.fusiontech.user.service;


import com.vnco.fusiontech.user.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role createRole(Role role);
    Optional<Role> getRole(String roleName);
    Role updateRole(String oldRole, String newRole);
    void deleteRole(Role roleName);

    List<Role> getAllRoles();
}
