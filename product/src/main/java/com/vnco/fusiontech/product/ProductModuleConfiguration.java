package com.vnco.fusiontech.product;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EntityScan (basePackages = "com.vnco.fusiontech.product.entity")
@EnableJpaRepositories (basePackages = "com.vnco.fusiontech.product.repository")
public class ProductModuleConfiguration {

}
