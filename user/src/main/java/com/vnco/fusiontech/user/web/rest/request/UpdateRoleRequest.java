package com.vnco.fusiontech.user.web.rest.request;

import com.vnco.fusiontech.common.constraint.NullOrNotEmpty;
import com.vnco.fusiontech.user.entity.roles.Roles;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateRoleRequest(
        String firebaseUid,
        @NotNull List<Roles> roles
) {
}
