package com.vnco.fusiontech.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@RequiredArgsConstructor
@Slf4j
@Configuration
@ComponentScan
@EntityScan(basePackages = "com.vnco.fusiontech.user.entity")
@EnableJpaRepositories(basePackages = "com.vnco.fusiontech.user.repository")
public class UserModuleConfiguration {
}
