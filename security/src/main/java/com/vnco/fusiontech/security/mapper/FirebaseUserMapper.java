package com.vnco.fusiontech.security.mapper;

import com.google.firebase.auth.UserRecord;
import com.vnco.fusiontech.common.web.request.CreateUserRecord;
import com.vnco.fusiontech.common.web.request.RegisterUserWithEmailRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface FirebaseUserMapper {

    @Mapping(target = "uid", ignore = true)
    @Mapping(target = "photoUrl", ignore = true)
    @Mapping(target = "phoneNumber", source = "phone")
    @Mapping(target = "emailVerified", constant = "true")
    @Mapping(target = "disabled", constant = "false")
    UserRecord.CreateRequest toFirebaseCreateRequest(RegisterUserWithEmailRequest request);

    @Mapping(target = "lastName", source = "request.lastName")
    @Mapping(target = "firstName", source = "request.firstName")
    @Mapping(target = "firebaseUid", source = "record.uid")
    @Mapping(target = "email", source = "request.email")
    CreateUserRecord toCreateUserDatabaseRecord(UserRecord record, RegisterUserWithEmailRequest request);

    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "firstName", source = "displayName")
    @Mapping(target = "firebaseUid", source = "uid")
    CreateUserRecord toCreateUserDatabaseRecord(UserRecord record);
}
