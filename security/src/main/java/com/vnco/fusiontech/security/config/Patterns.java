package com.vnco.fusiontech.security.config;

import com.vnco.fusiontech.common.constant.AuthoritiesConstant;

public enum Patterns {
    // ANONYMOUS - permitAll()

    PRODUCTS(AuthoritiesConstant.ANONYMOUS, "/api/products/**"),
    BRANDS(AuthoritiesConstant.ANONYMOUS, "/api/brands/**"),
    CATEGORIES(AuthoritiesConstant.ANONYMOUS, "/api/categories/**"),
    REVIEWS(AuthoritiesConstant.ANONYMOUS, "/api/reviews/**"),
    VOUCHERS(AuthoritiesConstant.ANONYMOUS, "/api/vouchers/**"),
    VARIANTS(AuthoritiesConstant.ANONYMOUS, "/api/variants/**"),
    REGISTRATION(AuthoritiesConstant.ANONYMOUS, "/api/auth/register/**"),
    BEST_SELLER(AuthoritiesConstant.ANONYMOUS, "/api/statistical/best-seller"),

    // CUSTOMER - authenticated()
    ORDERS(AuthoritiesConstant.USER, "/api/orders/**"),
    UPDATE_PROFILE(AuthoritiesConstant.USER, "/api/auth/update-profile/**"),

    // STAFF


    // ADMIN - hasAnyRole('ADMIN')
    ADMIN_PATTERN(AuthoritiesConstant.ADMIN, "/api/**");


    private final String pattern;
    private final String role;

    Patterns(String role, String pattern) {
        this.role = role;
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public String getRole() {
        return role;
    }
}
