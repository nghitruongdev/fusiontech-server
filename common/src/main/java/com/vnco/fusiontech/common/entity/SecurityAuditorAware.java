package com.vnco.fusiontech.common.entity;

import com.vnco.fusiontech.common.utils.SecurityUtils;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementation of {@link AuditorAware} based on Spring Security
 */
@Component
public class SecurityAuditorAware implements AuditorAware<AppUser> {
    @Override
    public @NotNull @org.jetbrains.annotations.NotNull Optional<AppUser> getCurrentAuditor() {
        return SecurityUtils.getCurrentUserLogin();
    }
}
