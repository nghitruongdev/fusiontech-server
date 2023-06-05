package com.vnco.fusiontech.app;

import com.vnco.fusiontech.cart.CartModuleConfiguration;
import com.vnco.fusiontech.common.CommonModuleConfiguration;
import com.vnco.fusiontech.product.ProductModuleConfiguration;
import com.vnco.fusiontech.user.UserModuleConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityDataConfiguration;
import org.springframework.context.annotation.Import;
@SpringBootApplication
@Import (
        {
                CommonModuleConfiguration.class,
                CartModuleConfiguration.class,
                ProductModuleConfiguration.class,
                UserModuleConfiguration.class,
                SecurityDataConfiguration.class
        }
)
public class FusionTechApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(FusionTechApplication.class, args);
    }
    
}
