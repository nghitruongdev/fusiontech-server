package com.vnco.fusiontech.user.entity.projection;

import com.vnco.fusiontech.user.entity.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "name-image-only", types = User.class)
public interface UserNameAndImageOnly {
    String getFullName();
    Object getImage();
}
