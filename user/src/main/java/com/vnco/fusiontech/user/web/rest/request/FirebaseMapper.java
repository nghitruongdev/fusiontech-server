package com.vnco.fusiontech.user.web.rest.request;

import com.google.firebase.auth.UserRecord;
import com.vnco.fusiontech.user.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.ERROR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FirebaseMapper {

    @Mapping(target = "uid", ignore = true)
    @Mapping(target = "emailVerified", constant = "true")
    @Mapping(target = "disabled", constant = "false")
    @Mapping(target = "password", defaultExpression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "photoUrl", source = "image")
    @Mapping(target = "displayName", expression = "java(com.vnco.fusiontech.common.utils.FirebaseUtils.composeFullName(request.firstName(),request.lastName()))")
    UserRecord.CreateRequest toFirebaseCreateRequest(UserRequest request);

    @Mapping(target = "providersToUnlink", ignore = true)
    @Mapping(target = "providerToLink", ignore = true)
    @Mapping(target = "customClaims", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "photoUrl", source = "request.image")
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "phoneNumber", source = "request.phoneNumber")
    // @Mapping (target = "email", source = "request.email", conditionExpression =
    // "java(request.email() != null && " +
    // "!request.email().equalsIgnoreCase(user.getEmail()))")
    // @Mapping (target = "phoneNumber", source = "request.phoneNumber",
    // conditionExpression = "java(request.phoneNumber()!=null &&
    // !request.phoneNumber().equals(user.getPhoneNumber()))")
    @Mapping(target = "displayName", expression = """
                                                  java(
                                                  com.vnco.fusiontech.common.utils.FirebaseUtils.composeFullName(request.firstName(),request.lastName(),user.getFirstName(), user.getLastName())
                                                  )""")
    UserRecord.UpdateRequest toFirebaseUpdateRequest(UserRequest request, User user,
            @MappingTarget UserRecord.UpdateRequest target);

}
