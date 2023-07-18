package com.vnco.fusiontech.user.repository;

import com.vnco.fusiontech.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    
    @RestResource (path = "many", rel = "many")
    List<User> findAllByIdIn(List<Long> ids);

//    Optional<User> findByEmail(String email);
    User findByFirebaseUid(String firebaseUid);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
