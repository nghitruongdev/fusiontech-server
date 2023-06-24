package com.vnco.fusiontech.order;

import com.vnco.fusiontech.common.service.PublicUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.UUID;

@Configuration
@ComponentScan
@Slf4j
@RequiredArgsConstructor
@EntityScan("com.vnco.fusiontech.order.entity")
@EnableJpaRepositories(basePackages = "com.vnco.fusiontech.order.repository")
public class OrderModuleConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public PublicUserService userService(){
        log.warn("{} is not implemented", "Public Product Variant Service");
        return new PublicUserService() {
            @Override
            public boolean existsById(UUID id) {
                log.warn("Exists by Id is not implemented: {}", id);
              return true;
            }
    
            @Override
            public boolean hasShippingAddress(UUID userId, Long addressId) {
                log.warn("has shipping address is not implemented: {}", addressId);
                return true;
            }
        };
    }
}
