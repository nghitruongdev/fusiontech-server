package com.vnco.fusiontech.user.repository;

import com.vnco.fusiontech.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

    @RestResource(path = "many", rel = "many")
    List<User> findAllByIdIn(@Param("ids") List<Long> ids);

    @RestResource
    boolean existsByEmail(@Param("email") String email);

    @RestResource
    boolean existsByPhoneNumber(@Param("phone") String phoneNumber);

    @RestResource(path = "findByFirebaseId")
    Optional<User> findByFirebaseUid(@Param("firebaseId") String firebaseUid);

}
