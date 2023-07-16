package com.vnco.fusiontech.order;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.vnco.fusiontech.common.service.PublicUserService;
import com.vnco.fusiontech.common.web.request.CreateUserRequest;
import com.vnco.fusiontech.common.web.request.RegisterUser;
import com.vnco.fusiontech.common.web.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
            public boolean isUserExisted(RegisterUser registerUser) {
                return false;
            }

            @Override
            public String getFirebaseUid(Long userId) {
                return null;
            }

            @Override
            public void register(CreateUserRequest request) {
            }

            @Override
            public void updateUser(UserUpdateRequest request, Long userId) {

            }

            @Override
            public String convertToE164Format(String phoneNumber) {
                return null;
            }

            @Override
            public String composeFullName(UserUpdateRequest request) {
                return null;
            }
        };
    }
}
