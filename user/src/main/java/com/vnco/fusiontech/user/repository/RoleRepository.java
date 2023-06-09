package com.vnco.fusiontech.user.repository;

import com.vnco.fusiontech.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

     Optional<Role> findByNameIgnoreCase(String name);

     @Modifying
     @Query("UPDATE Role r SET r.name = :newName WHERE LOWER(r.name) = LOWER(:existingName)")
     void updateRoleName(@Param("existingName") String existingName, @Param("newName") String newName);
}
