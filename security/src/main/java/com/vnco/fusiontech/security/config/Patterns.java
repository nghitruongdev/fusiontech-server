package com.vnco.fusiontech.security.config;

import com.vnco.fusiontech.common.constant.AuthoritiesConstant;

public enum Patterns {
    // ANONYMOUS - permitAll()
//    OK(AuthoritiesConstant.ANONYMOUS, "/api/**"),
    PRODUCTS(AuthoritiesConstant.ANONYMOUS, "/api/products/**"),
    BRANDS(AuthoritiesConstant.ANONYMOUS, "/api/brands/**"),
    CATEGORIES(AuthoritiesConstant.ANONYMOUS, "/api/categories/**"),
    REVIEWS(AuthoritiesConstant.ANONYMOUS, "/api/reviews/**"),
    VOUCHERS(AuthoritiesConstant.ANONYMOUS, "/api/vouchers/**"),
    VARIANTS(AuthoritiesConstant.ANONYMOUS, "/api/variants/**"),
    REGISTRATION(AuthoritiesConstant.ANONYMOUS, "/api/auth/register/**"),
    BEST_SELLER(AuthoritiesConstant.ANONYMOUS, "/api/statistical/best-seller"),
    ORDERS_STATUS(AuthoritiesConstant.ANONYMOUS, "/api/orders/statuses/**"),
    SPECIFICATION(AuthoritiesConstant.ANONYMOUS, "/api/specifications"),
    SEARCH_USER(AuthoritiesConstant.ANONYMOUS, "/api/users/**"),

    // CUSTOMER - authenticated()
    UPDATE_USER(AuthoritiesConstant.USER, "/api/users/**"),
    USER_REVIEW(AuthoritiesConstant.USER, "/api/reviews/**"),
    ORDERS(AuthoritiesConstant.USER, "/api/orders/**"),
    PRODUCT_USER(AuthoritiesConstant.USER, "/api/products/**"),
    PAYMENT(AuthoritiesConstant.USER, "/api/payments/**"),
    UPDATE_PROFILE(AuthoritiesConstant.USER, "/api/auth/update-profile"),
    SEARCH_ADDRESS(AuthoritiesConstant.USER, "/api/shippingAddresses/**"),
    CHECKOUT_CART(AuthoritiesConstant.USER, "/api/cart/**"),


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
