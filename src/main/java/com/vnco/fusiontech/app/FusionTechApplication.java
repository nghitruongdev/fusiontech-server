package com.vnco.fusiontech.app;

import com.github.javafaker.Faker;
import com.vnco.fusiontech.common.CommonModuleConfiguration;
import com.vnco.fusiontech.mail.MailConfiguration;
import com.vnco.fusiontech.mail.service.MailService;
import com.vnco.fusiontech.order.OrderModuleConfiguration;
import com.vnco.fusiontech.product.ProductModuleConfiguration;
import com.vnco.fusiontech.product.entity.*;
import com.vnco.fusiontech.product.repository.*;
import com.vnco.fusiontech.product.service.ProductService;
import com.vnco.fusiontech.security.SecurityModuleConfiguration;
import com.vnco.fusiontech.storage.StorageModuleConfiguration;
import com.vnco.fusiontech.user.UserModuleConfiguration;
import com.vnco.fusiontech.user.entity.ShippingAddress;
import com.vnco.fusiontech.user.repository.ShippingAddressRepository;
import com.vnco.fusiontech.user.repository.UserRepository;
import com.vnco.fusiontech.user.service.AuthService;
import com.vnco.fusiontech.user.web.rest.request.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequiredArgsConstructor
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ErrorMvcAutoConfiguration.class })
@Import({
        CommonModuleConfiguration.class,
        ProductModuleConfiguration.class,
        UserModuleConfiguration.class,
        OrderModuleConfiguration.class,
        SecurityModuleConfiguration.class,
        MailConfiguration.class,
        StorageModuleConfiguration.class
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
        private final BrandRepository          brandRepository;
        private final UserRepository           userRepository;
        private final SpecificationRepository  specificationRepository;
        private final MailService              mailService;
        private final AuthService              authService;
        private final UserMapper               userMapper;
        
        
        @Bean
        @Profile ("bootstrap")
        public CommandLineRunner bootstrapByScript() {
                log.debug("Brand size: {{}}", brandRepository.count());
                return args -> {
                        var users = authService.findAll().stream().map(userMapper::toUser).toList();
                        userRepository.saveAll(users);
                        users.forEach(user -> authService.setInitialClaims(user.getId(), user.getFirebaseUid()));
                        System.exit(200);
                };
        }
        private final List<String> imageList =Arrays.asList(images);
//                Arrays.stream(images).map(url -> FirebaseImage.builder().url(url).build()).toList();
        private String getRandomImage (){
               return imageList.get(faker.number().numberBetween(0, imageList.size()-1));
        }
        private List<String> getImages() {
                return imageList.stream().filter(image -> Math.random() > 0.5)
                                      .limit(faker.number().numberBetween(0, 3))
                                      .toList();
        }
//                @Bean
        @Profile ("bootstrap")
        @Lazy
        public CommandLineRunner bootstrapByCode(ProductRepository productRepository,
                                           ProductVariantRepository variantRepository,
                                           ShippingAddressRepository shippingAddressRepository,
                                           UserRepository userRepository,
                                           CategoryRepository categoryRepository,
                                           BrandRepository brandRepo,
                                           ProductService productService
        ) {
                return args -> {
                        var number = faker.number();
                        
                        List<Brand> brands = IntStream.rangeClosed(1, 20)
                                                      .mapToObj(i -> Brand.builder()
                                                                          .name(faker.leagueOfLegends().champion())
                                                                             .image(getRandomImage())
                                                                          .build())
                                                      .toList();
                        brandRepo.saveAll(brands);
                        
                        List<Category> categories = IntStream.rangeClosed(1, 20)
                                                             .mapToObj(i -> Category.builder()
                                                                                    .name(faker.commerce().department())
                                                                                    .slug(Math.random() + faker.code().asin())
                                                                                    .image(getRandomImage())
                                                                                    .description(faker.educator().campus())
                                                                                    .specifications(
                                                                                            List.of("Ram",
                                                                                                    "Bộ nhớ trong",
                                                                                                    "Màn hình"))
                                                                                    .build())
                                                             .toList();
                        categoryRepository.saveAll(categories);
                        
                        var ramSpecs = Stream.of("16GB", "32GB", "64GB").map(item -> Specification.builder().name(
                                "Ram").value(item).build()).toList();
                        var ssdSpecs = Stream.of("128GB", "256GB", "512GB", "1TB")
                                             .map(item -> Specification.builder().name(
                                                     "Bộ nhớ trong").value(item).build())
                                             .toList();
                        var displaySpecs =
                                Stream.of("13 inch", "15 inch", "17 inch").map(item -> Specification.builder().name(
                                        "Màn hình").value(item).build()).toList();
                        
                        List<Specification> list = new ArrayList<>();
                        List.of(ramSpecs, ssdSpecs, displaySpecs).forEach(list::addAll);
                        specificationRepository.saveAll(list);
                        
                        var features = List.of("Di động và tiện lợi", "Kết nối đa dạng", "Pin trâu, hiệu suất mạnh " +
                                                                                         "mẽ");
                        
                        List<Product> products = IntStream.rangeClosed(1, 20)
                                                          .mapToObj(i -> Product.builder()
                                                                                .name(faker.commerce().productName())
                                                                                .brand(brands.get(
                                                                                        number.numberBetween(0,
                                                                                                             brands.size() - 1)))
                                                                                .category(categories.get(
                                                                                        number.numberBetween(0,
                                                                                                             categories.size() - 1)))
                                                                                .description(String.join("\n\n", faker.lorem().paragraphs(
                                                                                        number.numberBetween(2, 4))))
                                                                                .features(features)
                                                                                .summary(
                                                                                        faker.lorem().paragraphs(1).get(0))
                                                                                .images(getImages())
                                                                                .slug(faker.code().asin())
                                                                                .build())
                                                          .toList();
                        productRepository.saveAll(products);
                        Supplier<Set<Specification>> getRandomSpecs = ()->
                                 Stream.of(ramSpecs, ssdSpecs, displaySpecs).filter(specs -> Math.random() > 0.5)
                                      .map(specs -> specs.get(number.numberBetween(0, specs.size() -1 )))
                                      .collect(Collectors.toSet());
                        ;
                        Function<Product, Stream<Variant>> createVariant = (product)-> IntStream.rangeClosed(1, 10)
                                                                                        .mapToObj(i-> Variant.builder()
                                                                                                            .images(getImages())
                                                                                                            .sku(UUID.randomUUID().toString())
                                                                                                            .price((double) number.numberBetween(10_000_000,
                                                                                                                                                 30_000_000))
                                                                                                            .product(product)
                                                                                                              .specifications(getRandomSpecs.get())
                                                                                                            .build());
                        List<Variant> variants =
                                products.stream().flatMap(createVariant).toList();
                        variantRepository.saveAll(variants);
                        
                        var users = authService.findAll().stream().map(userMapper::toUser).toList();
                        userRepository.saveAll(users);
                        users.forEach(user -> authService.setInitialClaims(user.getId(), user.getFirebaseUid()));
                        List<ShippingAddress> addresses = IntStream.rangeClosed(1, 3)
                                                                   .mapToObj(i -> ShippingAddress.builder()
                                                                                                 .address(
                                                                                                         faker.address()
                                                                                                              .streetAddress())
                                                                                                 .ward(faker.address()
                                                                                                            .city())
                                                                                                 .district(
                                                                                                         faker.address()
                                                                                                              .countryCode())
                                                                                                 .phone(faker.phoneNumber()
                                                                                                             .cellPhone())
                                                                                                 .name(faker.name()
                                                                                                            .fullName())
                                                                                                 .province(
                                                                                                         faker.address()
                                                                                                              .country())
                                                                                                 .user(users.get(0))
                                                                                                 .build())
                                                                   .toList();
                        shippingAddressRepository.saveAll(addresses);
                        System.exit(0);
                };
        }
        
//        @Autowired
//        @Lazy
//        private  PublicStorageService storageService;
//        private final  ReportRepository reportRepository;
//        @GetMapping("/api/test")
//        public ResponseEntity<?> removeImage(){
//                return ResponseEntity.of(Optional.ofNullable(reportRepository.topSpentCustomer()));
//        }
}
