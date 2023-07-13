package com.vnco.fusiontech.user.web.request;

import com.vnco.fusiontech.user.entity.Role;

import java.util.UUID;

public record AuthorityCreateRequest(Long userId, String roleName) {
}

