package com.vnco.fusiontech.user.web.request;

import com.vnco.fusiontech.user.entity.Role;

import java.util.UUID;

public record AuthorityCreateRequest(UUID userId, String roleName) {
}

