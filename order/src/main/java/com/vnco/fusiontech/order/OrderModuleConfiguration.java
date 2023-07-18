package com.vnco.fusiontech.order;

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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@ComponentScan
@Slf4j
@RequiredArgsConstructor
@EntityScan("com.vnco.fusiontech.order.entity")
@EnableJpaRepositories(basePackages = "com.vnco.fusiontech.order.repository")
public class OrderModuleConfiguration {
    // cấu hình tài khoản email
    public static final String MY_EMAIL = "tule0608@gmail.com";

    // Replace password!!
    public static final String MY_PASSWORD = "gontmkoiopkhicvr";

    // And receiver!
    public static final String FRIEND_EMAIL = "letu060802@gmail.com";

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(MY_EMAIL);
        mailSender.setPassword(MY_PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

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
