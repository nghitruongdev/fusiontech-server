package com.vnco.fusiontech.common.constant;

import java.util.List;

public interface AuthoritiesConstant {
    String ADMIN = "ADMIN";
    String USER = "USER";
    String ANONYMOUS = "ANONYMOUS";
    String STAFF = "STAFF";

    String SYSTEM = "SYSTEM";
    String ROLE_NAME = "roles";
    String ROLE_PREFIX = "ROLE_";

    static List<String> defaults() {
        return List.of(ADMIN, USER, ANONYMOUS, STAFF);
    }
}
