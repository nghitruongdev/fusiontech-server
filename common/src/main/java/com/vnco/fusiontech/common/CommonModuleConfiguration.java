package com.vnco.fusiontech.common;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EntityScan (basePackages = "com.vnco.fusiontech.common.entity")
public class CommonModuleConfiguration {
}
