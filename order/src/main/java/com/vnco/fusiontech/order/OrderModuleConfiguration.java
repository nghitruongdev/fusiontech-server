package com.vnco.fusiontech.order;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.common.web.request.RegisterRequest;
import com.vnco.fusiontech.common.web.request.UserUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;
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
            public boolean existsById(Long id) {
                log.warn("Exists by Id is not implemented: {}", id);
              return true;
            }
    
            @Override
            public boolean hasShippingAddress(Long userId, Long addressId) {
                log.warn("has shipping address is not implemented: {}", addressId);
                return true;
            }

            @Override
            public void register(FirebaseToken decodedToken) {
            }

            @Override
            public void updateUser(UserUpdateRequest token, FirebaseToken decodedToken) throws FirebaseAuthException {

            }


        };
    }
}
