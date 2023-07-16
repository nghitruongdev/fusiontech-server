package com.vnco.fusiontech.app;

import com.github.javafaker.Faker;
import com.vnco.fusiontech.common.CommonModuleConfiguration;
import com.vnco.fusiontech.mail.MailConfiguration;
import com.vnco.fusiontech.mail.service.MailService;
import com.vnco.fusiontech.order.OrderModuleConfiguration;
import com.vnco.fusiontech.product.ProductModuleConfiguration;
import com.vnco.fusiontech.product.entity.Brand;
import com.vnco.fusiontech.product.entity.Category;
import com.vnco.fusiontech.product.entity.Specification;
import com.vnco.fusiontech.product.entity.Variant;
import com.vnco.fusiontech.product.repository.BrandRepository;
import com.vnco.fusiontech.product.repository.CategoryRepository;
import com.vnco.fusiontech.product.repository.ProductRepository;
import com.vnco.fusiontech.product.repository.ProductVariantRepository;
import com.vnco.fusiontech.product.service.ProductService;
import com.vnco.fusiontech.security.SecurityModuleConfiguration;
import com.vnco.fusiontech.user.UserModuleConfiguration;
import com.vnco.fusiontech.user.entity.ShippingAddress;
import com.vnco.fusiontech.user.entity.User;
import com.vnco.fusiontech.user.repository.ShippingAddressRepository;
import com.vnco.fusiontech.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, ErrorMvcAutoConfiguration.class })
@Import({
                CommonModuleConfiguration.class,
                ProductModuleConfiguration.class,
                UserModuleConfiguration.class,
                OrderModuleConfiguration.class,
                SecurityModuleConfiguration.class,
                MailConfiguration.class
})
@EntityScan(basePackages = "com.vnco.fusiontech.common.entity")
public class FusionTechApplication {
        private final ProductRepository productRepository;

        public static void main(String[] args) {
                SpringApplication.run(FusionTechApplication.class, args);
        }

        private final Faker faker = new Faker();

        private final String[] images = {
                        "https://i.ibb.co/1r28gMk/1.webp",
                        "https://i.ibb.co/qdfB3s6/2.webp",
                        "https://i.ibb.co/VL1Dnv1/4.webp",
                        "https://i.ibb.co/5F3nWv6/7.webp",
                        "https://i.ibb.co/xgZWmdq/8.jpg",
                        "https://i.ibb.co/rQKjVC2/5.webp",
                        "https://i.ibb.co/Ycz8hkV/6.webp",
                        "https://i.ibb.co/BLCDw7v/3.webp",
                        "https://i.ibb.co/qCXcPhq/8.webp",
                        "https://i.ibb.co/TTS9wY4/9.webp",
                        "https://i.ibb.co/BVzsqvz/10.webp",
                        "https://i.ibb.co/zPDcCQY/top4.webp",
                        "https://i.ibb.co/QC4L3RF/top8.jpg",
                        "https://i.ibb.co/dKmw2sC/top2.webp",
                        "https://i.ibb.co/sJwg0YF/top1.webp",
                        "https://i.ibb.co/v1sPXLq/top5.webp",
                        "https://i.ibb.co/yPJjB3r/top6.webp",
                        "https://i.ibb.co/zmw8xFY/top7.webp",
                        "https://i.ibb.co/vHJkwzt/top3.webp",
                        "https://i.ibb.co/BNXTLkq/12.webp"
        };
        
        private final ProductVariantRepository variantRepository;
        private final MailService mailService;
        @GetMapping("/api/test")
        @Transactional
        public void test(){
//                Variant variant = Variant.builder().sku(UUID.randomUUID().toString()).build();
//                var savedSpec = Specification.builder().id(2L).name("Display").value("6.9 inch").build();
//                var spec = Specification.builder().id(2L).name("Display").value("15 inch").build();
//
//                variant.addSpecification(savedSpec);
//                variant.addSpecification(spec);
//                variantRepository.save(variant);
        }
                @Bean
        @Profile("bootstrap")
        public CommandLineRunner bootstrap(ProductRepository productRepository,
                        ProductVariantRepository variantRepository,
                        ShippingAddressRepository shippingAddressRepository,
                        UserRepository userRepository,
                        CategoryRepository categoryRepository,
                        BrandRepository brandRepo,
                        ProductService productService) {
                return args -> {
                        var number = faker.number();

                        List<Brand> brands = IntStream.rangeClosed(1, 20)
                                        .mapToObj(i -> Brand.builder()
                                                        .name(faker.leagueOfLegends().champion())
                                                        .build())
                                        .toList();
                        brandRepo.saveAll(brands);

                        List<Category> categories = IntStream.rangeClosed(1, 20)
                                        .mapToObj(i -> Category.builder()
                                                               .name(faker.commerce().department())
                                                               .slug(Math.random() + faker.code().asin())
                                                               .description(faker.educator().campus())
                                                               .categorySpecs(
                                                                       List.of("Ram", "Bộ nhớ trong", "Cấu hình máy"))
                                                               .build())
                                                             .toList();
                        categoryRepository.saveAll(categories);
        
                        List<User> users = IntStream.rangeClosed(1, 10)
                                                    .mapToObj(i -> User.builder()
                                                                       .firebaseUid(UUID.randomUUID().toString())
                                                                       .build())
                                                    .toList();
                        userRepository.saveAll(users);
                        var ramSpecs =
                                Stream.of("16GB", "32GB", "64GB").map(item -> Specification.builder().name(
                                        "Ram").value(item).build()).toList();
                        var ssdSpecs =
                                Stream.of("128GB", "256GB", "512GB", "1TB")
                                      .map(item -> Specification.builder().name(
                                            "Bộ nhớ trong").value(item).build())
                                      .toList();
                        var displaySpecs = Specification.builder().name("Display").value("6.9 inch").build();
                        var imageList = Arrays.stream(images).filter(image -> Math.random() > 0.5)
                                              .limit(number.numberBetween(2, 5)).toList();
                        var variant = Variant.builder().images(imageList)
                                             .sku(UUID.randomUUID().toString())
                                             .price(number.numberBetween(10_000_000, 30_000_000)).build();
                        var variant2 = Variant.builder().images(imageList)
                                             .sku(UUID.randomUUID().toString())
                                             .price(number.numberBetween(10_000_000, 30_000_000)).build();
                        ramSpecs.forEach(variant::addSpecification);
                        variant.addSpecification(displaySpecs);

                        ramSpecs.forEach(variant2::addSpecification);
                        variant2.addSpecification(displaySpecs);
                        
//                        variantRepository.saveAll(List.of(variant, variant2));
                        //                        var attributeNames = List.of("Ram", "Bộ Nhớ Trong", "Phiên bản");
                        //                        var productRequest = IntStream.rangeClosed(1, 100)
                        //                                        .mapToObj(i -> {
                        //                                                var localNames = new ArrayList<>
                        //                                                (attributeNames);
                        //                                                var attributes = IntStream.rangeClosed(1, 2)
                        //                                                                .mapToObj(ai ->
                        //                                                                ProductAttributeRequest
                        //                                                                .builder()
                        //                                                                                .name
                        //                                                                                (localNames
                        //                                                                                .remove(
                        //                                                                                                number.numberBetween(0,
                        //                                                                                                                localNames.size()
                        //                                                                                                                                - 1)))
                        //                                                                                .values
                        //                                                                                (IntStream
                        //                                                                                .rangeClosed(1, 3)
                        //                                                                                                .mapToObj(vi -> faker
                        //                                                                                                                .book()
                        //                                                                                                                .title())
                        //                                                                                                .toList())
                        //                                                                                .build())
                        //                                                                .toList();
                        //
                        //                                                var request = CreateProductRequest.builder()
                        //                                                                .name(faker.commerce()
                        //                                                                .productName())
                        //                                                                .description(faker.commerce
                        //                                                                ().material())
                        //                                                                .category(categories.get
                        //                                                                (number.numberBetween(0,
                        //                                                                                categories
                        //                                                                                .size() - 1)))
                        //                                                                .thumbnail(new
                        //                                                                FirebaseImage("",
                        //                                                                                images[number.numberBetween(0,
                        //                                                                                                images.length - 1)]))
                        //                                                                .features(List.of("Feature
                        //                                                                1", "Feature 2",
                        //                                                                                "Feature 3"))
                        //                                                                .summary(faker.commerce()
                        //                                                                .department())
                        //                                                                .brand(brands.get(number
                        //                                                                .numberBetween(0,
                        //                                                                                brands.size
                        //                                                                                () - 1)))
                        //                                                                .attributes(attributes)
                        //
                        //                                                                .build();
                        //                                                return request;
                        //                                        }).toList();
                        //                        productRequest.forEach(productService::createProduct);
        
                        // var products = productRepository.findAll();
        
                        //                        var variants = variantRepository.findAll();
                        //
                        //                        variants.forEach(variant -> {
                        //                                var imageList = Arrays.stream(images).filter(image -> Math.random() > 0.5)
                        //                                                .limit(number.numberBetween(2, 5)).toList();
                        //                                variant.setImages(imageList);
                        //                                variant.setSku(UUID.randomUUID().toString());
                        //                                variant.setPrice(number.numberBetween(10_000_000, 30_000_000));
                        //                        });
                        //
                        //                        variantRepository.saveAll(variants);
                        // products.forEach(product -> product.addFavoriteUser((new
                        // com.vnco.fusiontech.product.entity.proxy.User(
                        // users.get(faker.random().nextInt(1, 9)).getId()))
                        // ));
        
                        // List<Variant> variants = IntStream.rangeClosed(1, 10)
                        // .mapToObj(i -> {
                        // Variant variant = new Variant();
                        // variant.setPrice(number.numberBetween(100, 1000));
                        // variant.setProduct(products.get(0));
                        // return variant;
                        // }).toList();
                        //
                        // variantRepository.saveAll(variants);

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
                                                        .build())
                                        .toList();
                        shippingAddressRepository.saveAll(addresses);
                };
        }
}
