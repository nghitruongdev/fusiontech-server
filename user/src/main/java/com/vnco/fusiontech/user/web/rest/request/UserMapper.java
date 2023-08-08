package com.vnco.fusiontech.user.web.rest.request;

import com.google.firebase.auth.UserRecord;
import com.vnco.fusiontech.user.entity.User;
import org.mapstruct.*;

@Mapper (
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface UserMapper {
    
    User toUser(UserRequest request);
    
    @Mapping (
            target = "image",
        source = "photoUrl"
//            expression = "java(new com.vnco.fusiontech.common.entity.FirebaseImage(null, null, source.getPhotoUrl()))"
    )
    @Mapping (target = "firebaseUid", source = "uid")
    @Mapping (target = "firstName", source = "displayName")
    User toUser(UserRecord source);
    
    User partialUpdate(UserRequest request, @MappingTarget User user);
}
