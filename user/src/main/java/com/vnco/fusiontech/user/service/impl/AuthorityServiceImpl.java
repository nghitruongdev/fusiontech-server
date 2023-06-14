package com.vnco.fusiontech.user.service.impl;

import com.vnco.fusiontech.user.entity.Authority;
import com.vnco.fusiontech.user.entity.Role;
import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.exception.InvalidRoleException;
import com.vnco.fusiontech.user.repository.AuthorityRepository;
import com.vnco.fusiontech.user.repository.UserRepository;
import com.vnco.fusiontech.user.service.AuthorityService;
import com.vnco.fusiontech.common.constant.AuthoritiesConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j

public class AuthorityServiceImpl implements AuthorityService {
    final
    AuthorityRepository repo;
    final UserRepository userRepo;

    public AuthorityServiceImpl(AuthorityRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }


    @Override
    public void updateRoleReference(Role oldRole, Role newRole) {
        // Find all authorities with old role name
        List<Authority> authorities = repo.findByRole(oldRole);

        // update the role reference for each authority
        authorities.forEach(authority -> {
            authority.setRole(newRole);
            repo.save(authority);
        });
    }

    @Override
    public List<Authority> getAllAuthorities() {
        return repo.findAll();
    }

    @Override
    public Optional<Authority> getAuthorityById(Long id) {
        Optional<Authority> authority = repo.findById(id);
        if (authority.isPresent()) {
            throw new NullPointerException("Authority id is null");
        }
        return authority;
    }

    @Override
    public Authority createAuthority(UUID userId, String roleName) {
        // check authority is null or empty
        if (userId == null || roleName == null)
            throw new InvalidRoleException("Can not create authority with null user or role");

        // check if role is valid
        if (!isValidRole(roleName)) throw new InvalidRoleException("Invalid role");

        // check if an authority with the given user and role already exists
        Authority existingAuthority = repo.findByUserIdAndRoleName(userId, roleName);
        if (existingAuthority != null) throw new IllegalStateException("User already has this role");

        // Instantiate a new authority
        Authority authority = new Authority();
        // Set the user and role for the authority
        authority.setUser(User.builder().id(userId).build());
        authority.setRole(Role.builder().name(roleName).build());
        // Save the authority to the database
        repo.save(authority);

        return authority;
    }

    @Override
    public void deleteAuthority(Long authId) {
        if (repo.findById(authId).isPresent()) {
            repo.deleteById(authId);
        } else {
            log.error("Authority with id: {} does not exist", authId);
        }
    }

    @Override
    public Authority updateUserRole(User userId, Role role) {
        return null;
    }

    boolean isValidRole(String role) {
        return  role.equals(AuthoritiesConstant.ADMIN) ||
                role.equals(AuthoritiesConstant.USER) ||
                role.equals(AuthoritiesConstant.ANONYMOUS);
    }
}
