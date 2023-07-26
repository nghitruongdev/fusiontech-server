package com.vnco.fusiontech.common.constant;

import java.util.List;

public interface AuthoritiesConstant {
    String ADMIN = "admin";
    String USER = "user";
    String ANONYMOUS = "anonymous";
    
    String SYSTEM = "SYSTEM";
    String ROLE_NAME = "roles";
    String ROLE_PREFIX = "ROLE_";

    static List<String> defaults() {
        return List.of(ADMIN, USER, ANONYMOUS);
    }

}

enum Authorities {
    ADMIN("admin"),
    USER("user"),
    ANONYMOUS("anonymous");

    private final String value;

    Authorities(String value) {
        this.value = value;
    }

}
