package com.vnco.fusiontech.user.service.impl;


import com.vnco.fusiontech.user.entity.Role;
import com.vnco.fusiontech.user.exception.InvalidRoleException;
import com.vnco.fusiontech.user.repository.RoleRepository;
import com.vnco.fusiontech.user.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    // injecting the RoleRepository
    private final RoleRepository repo;

    public RoleServiceImpl(RoleRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Role> getAllRoles() {
        return repo.findAll();
    }

    /**
     * Save user role to database
     * @param role new role to be saved
     * @return saved role
     */
    @Override
    public Role createRole(Role role) {
        // check if role is null or empty
        if (role == null || role.getName().isEmpty() || repo.findByNameIgnoreCase(role.getName()).isPresent()) {
            throw new InvalidRoleException("Already existed role");
        }

        // check if role name is existed
        Optional<Role> roleOptional = repo.findByNameIgnoreCase(role.getName());

        if (roleOptional.isPresent()) {
            throw new InvalidRoleException("Already existed role");
        }
        return repo.save(new Role(role.getName().toUpperCase()));
    }


    /**
     * Get a specific role.
     * @param roleName which is a string, this method will take this string and find the role with the same name.
     * @throws InvalidRoleException if role name is not valid.
     * @return an {@link Optional<Role>} , existed role.
     */
    @Override
    public Optional<Role> getRole(String roleName) {
        Optional<Role> myOptional = repo.findByNameIgnoreCase(roleName);
        if (myOptional.isPresent()) {
            return myOptional;
        } else {
            throw new InvalidRoleException("Role name is not valid");
        }
    }

    /**
     * Update a role.
     * @param oldRoleName which is a string, this method will take this string and find the role with the same name.
     * @param newRoleName this is the new role name that will be updated.
     *                    this method will remove the old role and create a new role with the new name.
     * @return new role and delete the old role.
     */
    @Override
    public Role updateRole(String oldRoleName, String newRoleName) {
        // check null or empty
        if (newRoleName == null || newRoleName.isEmpty() || repo.findByNameIgnoreCase(newRoleName).isPresent()) {
            throw new IllegalArgumentException("Invalid role name");
        }

        // Find existing role
        Optional<Role> oldRoleOptional = repo.findByNameIgnoreCase(oldRoleName);
        if (oldRoleOptional.isEmpty()) {
            log.debug("Old role name not found" + oldRoleName);
            throw new InvalidRoleException("Old role name not found");
        }

        // create a new role with a new name
        Role newRole = new Role(newRoleName.toUpperCase());
        repo.save(newRole);
        log.info("New role created: " + newRole.getName());
        // delete the old role
        repo.delete(oldRoleOptional.get());

        return newRole;
    }

    /**
     * Delete a role from database.
     * @param roleName an object of {@link Role} which is the role name to be deleted.
     * @throws InvalidRoleException if role name is not valid.
     */
    @Override
    public void deleteRole(Role roleName) {
        if (roleName == null || roleName.getName().isEmpty()) {
            throw new IllegalArgumentException("Role name cannot be blank");
        }

        Optional<Role> myOptional = repo.findByNameIgnoreCase(roleName.getName());

        if (myOptional.isPresent()) {
            repo.delete(roleName);
        } else {
            throw new InvalidRoleException("Role name is not valid");
        }
    }
}
