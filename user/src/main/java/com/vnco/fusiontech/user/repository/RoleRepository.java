package com.vnco.fusiontech.user.repository;

import com.vnco.fusiontech.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

//@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, String> {

     Optional<Role> findByNameIgnoreCase(String name);


     /**
      * The custom query is used to define an update operation to change the name of a role in the database
      * @param existingName
      *
      * @param newName
      * parameters are named parameters that correspond to the existingName and newName arguments of the {@code updateRoleName} method, respectively.
      */

}
