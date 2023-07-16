package com.vnco.fusiontech.security.mapper;

import com.google.firebase.auth.FirebaseToken;
import com.vnco.fusiontech.common.web.request.CreateUserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FirebaseUserMapper {
    CreateUserRequest toRequest(FirebaseToken token);
}
