package com.vnco.fusiontech.user.repository;

import com.vnco.fusiontech.user.entity.User;
import org.hibernate.boot.model.source.spi.Sortable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    boolean existsByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @RestResource(path = "findByFirebaseId")
    Optional<User> findByFirebaseUid(@Param("firebaseId") String firebaseUid);
    
    @RestResource
    Optional<User> findByEmail(String email);

    @RestResource(path = "customers")
    @Query("FROM User u WHERE u.isStaff = FALSE")
    Page<User> findCustomer(Pageable page);

    @RestResource(path = "staffs")
    @Query("FROM User u WHERE u.isStaff = TRUE")
    Page<User> findStaff(Pageable page);

    @RestResource(path = "find-by-email")
    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<User> findUserByEmail(@Param("email") String email);

    @RestResource(path = "find-by-phone")
    Optional<User> findUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT COUNT(u.id) FROM User u WHERE u.isDisabled = false")
    Long countAllUsers();

}
