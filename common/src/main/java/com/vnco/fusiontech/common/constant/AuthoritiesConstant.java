package com.vnco.fusiontech.common.constant;

import java.util.List;

public interface AuthoritiesConstant {
    String ADMIN = "ROLE_ADMIN";
    String USER = "ROLE_USER";
    String ANONYMOUS = "ROLE_ANONYMOUS";

    String ROLE_NAME = "role";
    String ROLE_PREFIX = "ROLE_";

    static List<String> defaults() {
        return List.of(ADMIN, USER, ANONYMOUS);
    }
}
