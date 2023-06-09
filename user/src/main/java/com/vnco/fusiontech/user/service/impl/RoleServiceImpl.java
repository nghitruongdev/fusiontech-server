package com.vnco.fusiontech.user.service.impl;


import com.vnco.fusiontech.user.entity.Role;
import com.vnco.fusiontech.user.exception.InvalidRoleException;
import com.vnco.fusiontech.user.repository.RoleRepository;
import com.vnco.fusiontech.user.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    // injecting the RoleRepository
    private final RoleRepository repo;

    public RoleServiceImpl(RoleRepository repo) {
        this.repo = repo;
    }

    @Override
    public Role createRole(Role role) {
        return repo.save(role);
    }

    @Override
    public Optional<Role> getRole(String roleName) {
        Optional<Role> myOptional = repo.findByNameIgnoreCase(roleName);
        if (myOptional.isPresent()) {
            return myOptional;
        } else {
            throw new InvalidRoleException("Role name is not valid");
        }
    }

    //TODO: Something is wrong with this method, try to find out what is wrong
    @Override
    public Role updateRole(String updateRole) {
        // check null or empty
        if (updateRole == null || updateRole.isEmpty()) {
            throw new IllegalArgumentException("Invalid role name");
        }

        // check if role name exists
        Optional<Role> roleOptional = repo.findByNameIgnoreCase(updateRole);
        //TODO this is the problem
        if (roleOptional.isPresent()) {
            String existingRoleName = roleOptional.get().getName();
            // check if role name is the same
            if (!existingRoleName.equalsIgnoreCase(updateRole)) {
                repo.updateRoleName(existingRoleName, updateRole);
            }

            Role updatedRole = new Role();
            updatedRole.setName(updateRole);
            return updatedRole;
        } else {
            throw new InvalidRoleException("Role name is not valid");
        }
//        Optional<Role> myOptional = repo.findByNameIgnoreCase(updateRole);
//        if (myOptional.isPresent()) {
//            Role existingRole = myOptional.get();
//            // update the role name
//            existingRole.setName(updateRole);
//            // save the updated role to database
//            return repo.save(existingRole);
//        } else {
//            throw new InvalidRoleException("Role name is not valid");
//        }
    }

    @Override
    public void deleteRole(Role roleName) {
        if (roleName == null || roleName.getName().isEmpty()) {
            throw new IllegalArgumentException("Invalid role name");
        }

        Optional<Role> myOptional = repo.findByNameIgnoreCase(roleName.getName());

        if (myOptional.isPresent()) {
            repo.delete(roleName);
        } else {
            throw new InvalidRoleException("Role name is not valid");
        }
    }
}
