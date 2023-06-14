package com.vnco.fusiontech.order;

import com.vnco.fusiontech.common.entity.AppUser;
import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.order.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;
import java.util.UUID;

@Configuration
@ComponentScan
@EnableJpaRepositories(basePackages = "com.vnco.fusiontech.order.repository")
@Slf4j
@RequiredArgsConstructor
public class OrderModuleConfiguration {
    
    private final AppUserRepository userRepository;
    
    @Bean
    @ConditionalOnMissingBean
    public PublicUserService userService(){
        return new PublicUserService() {
            @Override
            public boolean existsById(UUID id) {
                log.warn("Exists by Id is not implemented: {}", id);
               return  userRepository.existsById(id);
            }
    
            @Override
            public Optional<AppUser> findById(UUID id) {
                log.warn("Public user service is not implemented");
                return userRepository.findById(id);
            }
        };
    }
}
