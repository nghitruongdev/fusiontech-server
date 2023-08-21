package com.vnco.fusiontech.user.entity.projection;


import com.vnco.fusiontech.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = User.class, name = "user-roles")
public interface UserWithRole {
    String getFirebaseUid();

    String getFullName();

    String getEmail();

    @Value("#{@authServiceImpl.getUserRoles(target.firebaseUid)}")
    Object getRoles();
}
