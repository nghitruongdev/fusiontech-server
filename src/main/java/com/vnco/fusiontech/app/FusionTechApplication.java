package com.vnco.fusiontech.app;

import com.github.javafaker.Faker;
import com.vnco.fusiontech.cart.CartModuleConfiguration;
import com.vnco.fusiontech.common.CommonModuleConfiguration;
import com.vnco.fusiontech.common.entity.AppUser;
import com.vnco.fusiontech.common.entity.ShippingAddress;
import com.vnco.fusiontech.order.OrderModuleConfiguration;
import com.vnco.fusiontech.order.repository.AppUserRepository;
import com.vnco.fusiontech.order.repository.ShippingAddressRepository;
import com.vnco.fusiontech.product.ProductModuleConfiguration;
import com.vnco.fusiontech.product.entity.ProductVariant;
import com.vnco.fusiontech.product.repository.ProductRepository;
import com.vnco.fusiontech.product.repository.ProductVariantRepository;
import com.vnco.fusiontech.user.UserModuleConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityDataConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
@Import (
        {
                CommonModuleConfiguration.class,
                CartModuleConfiguration.class,
                ProductModuleConfiguration.class,
                UserModuleConfiguration.class,
                SecurityDataConfiguration.class,
                OrderModuleConfiguration.class
        }
)
@EntityScan (basePackages = "com.vnco.fusiontech.common.entity")
public class FusionTechApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(FusionTechApplication.class, args);
    }
    
    private final Faker faker = new Faker();
    
    @Bean
    @Profile ("bootstrap")
    public CommandLineRunner bootstrap(ProductRepository productRepository, ProductVariantRepository variantRepository,
                                       AppUserRepository userRepository,
                                       ShippingAddressRepository shippingAddressRepository
    ) {
        return args -> {
            var number = faker.number();
            List<ProductVariant> variants = IntStream.rangeClosed(1, 10)
                                                     .mapToObj(i -> {
                                                         ProductVariant variant = new ProductVariant();
                                                         variant.setPrice(number.numberBetween(100, 1000));
                                                         variant.setStock_quantity(number.numberBetween(100, 500));
                                                         variant.setAvailable_quantity(
                                                                 variant.getStock_quantity() - number.numberBetween(10,
                                                                                                                    50));
                                                         return variant;
                                                     }).toList();
            
            variantRepository.saveAll(variants);
            
            List<AppUser> users = IntStream.rangeClosed(1, 10)
                                           .mapToObj(i -> AppUser.builder()
                                                             .build()).toList();
            userRepository.saveAll(users);
            
            List<ShippingAddress> addresses = IntStream.rangeClosed(1, 10)
                                                       .mapToObj(i -> ShippingAddress.builder()
                                                                                 .build()).toList();
            shippingAddressRepository.saveAll(addresses);
        };
    }
}
