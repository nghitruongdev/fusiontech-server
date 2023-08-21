package com.vnco.fusiontech.common.constant;

import java.util.List;

public interface AuthoritiesConstant {
    String ADMIN = "admin";
    String USER = "user";
    String ANONYMOUS = "anonymous";
    String STAFF = "staff";

    String SYSTEM = "SYSTEM";
    String ROLE_NAME = "roles";
    String ROLE_PREFIX = "ROLE_";

    static List<String> defaults() {
        return List.of(ADMIN, USER, ANONYMOUS, STAFF);
    }
}
