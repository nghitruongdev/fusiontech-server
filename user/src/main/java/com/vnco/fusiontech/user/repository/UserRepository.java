package com.vnco.fusiontech.user.repository;

import com.vnco.fusiontech.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

//@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID id);
}
