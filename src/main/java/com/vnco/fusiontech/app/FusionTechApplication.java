package com.vnco.fusiontech.app;

import com.github.javafaker.Faker;
import com.vnco.fusiontech.cart.CartModuleConfiguration;
import com.vnco.fusiontech.common.CommonModuleConfiguration;
import com.vnco.fusiontech.order.OrderModuleConfiguration;
import com.vnco.fusiontech.product.ProductModuleConfiguration;
import com.vnco.fusiontech.product.entity.Category;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Variant;
import com.vnco.fusiontech.product.repository.CategoryRepository;
import com.vnco.fusiontech.product.repository.ProductRepository;
import com.vnco.fusiontech.product.repository.ProductVariantRepository;
import com.vnco.fusiontech.security.SecurityModuleConfiguration;
import com.vnco.fusiontech.user.UserModuleConfiguration;
import com.vnco.fusiontech.user.entity.ShippingAddress;
import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.repository.ShippingAddressRepository;
import com.vnco.fusiontech.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication (exclude = {SecurityAutoConfiguration.class, ErrorMvcAutoConfiguration.class})
@Import (
        {
                CommonModuleConfiguration.class,
                CartModuleConfiguration.class,
                ProductModuleConfiguration.class,
                UserModuleConfiguration.class,
                OrderModuleConfiguration.class,
                SecurityModuleConfiguration.class
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
                                       ShippingAddressRepository shippingAddressRepository,
                                       UserRepository userRepository,
                                       CategoryRepository categoryRepository
    ) {
        return args -> {
            var number = faker.number();
    
            List<Category> categories = IntStream.rangeClosed(1, 20)
                                                 .mapToObj(i -> Category.builder()
                                                                        .name(faker.commerce().department())
                                                                        .slug(Math.random() + faker.code().asin())
                                                                        .description(faker.educator().campus())
                                                                        .image("")
                                                                        .build())
                                                 .toList();
            categoryRepository.saveAll(categories);
    
            List<User> users = IntStream.rangeClosed(1, 10)
                                        .mapToObj(i -> User.builder()
                                                           .build()).toList();
            userRepository.saveAll(users);
    
            var products = IntStream.rangeClosed(1, 100)
                                    .mapToObj(i -> {
                                                  var product = Product.builder()
                                                                       .name(faker.commerce().productName())
                                                                       .image("")
                                                                       .description(faker.commerce().productName())
                                                                       .category(categories.get(number.numberBetween(0,
                                                                                                                     categories.size() - 1)))
                                                                       //                                  .brand()
                                                                       //                                   .category()
                                                                       //                                   .id()
                                                                       .build();
                                                  product.addFavoriteUser((new com.vnco.fusiontech.product.entity.proxy.User(
                                                          users.get(faker.random().nextInt(1, 9)).getId()))
                                                  );
                                                  return product;
                                              }
                                    ).toList();
            productRepository.saveAll(products);
    
            List<Variant> variants = IntStream.rangeClosed(1, 10)
                                              .mapToObj(i -> {
                                                         Variant variant = new Variant();
                                                         variant.setPrice(number.numberBetween(100, 1000));
                                                         variant.setProduct(products.get(number.numberBetween(0,
                                                                                                              products.size() -1)));
                                                         return variant;
                                                     }).toList();
    
            variantRepository.saveAll(variants);
    
            List<ShippingAddress> addresses = IntStream.rangeClosed(1, 3)
                                                       .mapToObj(i -> ShippingAddress.builder()
                                                                                     .address(faker.address()
                                                                                                   .streetAddress())
                                                                                     .ward(faker.address().city())
                                                                                     .district(faker.address()
                                                                                                    .countryCode())
                                                                                     .phone(faker.phoneNumber()
                                                                                                 .cellPhone())
                                                                                     .name(faker.name().fullName())
                                                                                     .province(
                                                                                             faker.address().country())
                                                                                     .user(users.get(0))
                                                                                     .build()).toList();
            shippingAddressRepository.saveAll(addresses);
        };
    }
}
