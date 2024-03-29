package com.vnco.fusiontech.app;

import com.github.javafaker.Faker;
import com.vnco.fusiontech.common.CommonModuleConfiguration;
import com.vnco.fusiontech.common.entity.AppUser;
import com.vnco.fusiontech.common.service.PublicMailService;
import com.vnco.fusiontech.mail.MailConfiguration;
import com.vnco.fusiontech.order.OrderModuleConfiguration;
import com.vnco.fusiontech.product.ProductModuleConfiguration;
import com.vnco.fusiontech.product.entity.Product;
import com.vnco.fusiontech.product.entity.Specification;
import com.vnco.fusiontech.product.entity.Variant;
import com.vnco.fusiontech.product.entity.VariantInventory;
import com.vnco.fusiontech.product.entity.projection.ProductSpecificationDTO;
import com.vnco.fusiontech.product.repository.*;
import com.vnco.fusiontech.product.service.ProductService;
import com.vnco.fusiontech.product.service.ProductVariantService;
import com.vnco.fusiontech.security.SecurityModuleConfiguration;
import com.vnco.fusiontech.storage.StorageModuleConfiguration;
import com.vnco.fusiontech.user.UserModuleConfiguration;
import com.vnco.fusiontech.user.repository.ShippingAddressRepository;
import com.vnco.fusiontech.user.repository.UserRepository;
import com.vnco.fusiontech.user.service.AuthService;
import com.vnco.fusiontech.user.web.rest.request.UserMapper;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;
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
@EnableScheduling
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
        private final PublicMailService              mailService;
        private final AuthService              authService;
        private final UserMapper               userMapper;
        private final ProductVariantService variantService;
        
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
                        
//                        List<Brand> brands = IntStream.rangeClosed(1, 20)
//                                                      .mapToObj(i -> Brand.builder()
//                                                                          .name(faker.leagueOfLegends().champion())
//                                                                             .image(getRandomImage())
//                                                                          .build())
//                                                      .toList();
//                        brandRepo.saveAll(brands);
//
//                        List<Category> categories = IntStream.rangeClosed(1, 20)
//                                                             .mapToObj(i -> Category.builder()
//                                                                                    .name(faker.commerce().department())
//                                                                                    .slug(Math.random() + faker.code().asin())
//                                                                                    .image(getRandomImage())
//                                                                                    .description(faker.educator().campus())
//                                                                                    .specifications(
//                                                                                            List.of("Ram",
//                                                                                                    "Bộ nhớ trong",
//                                                                                                    "Màn hình"))
//                                                                                    .build())
//                                                             .toList();
//                        categoryRepository.saveAll(categories);
                        
//                        var ramSpecs = Stream.of("16GB", "32GB", "64GB").map(item -> Specification.builder().name(
//                                "Ram").value(item).build()).toList();
//                        var ssdSpecs = Stream.of("128GB", "256GB", "512GB", "1TB")
//                                             .map(item -> Specification.builder().name(
//                                                     "Bộ nhớ trong").value(item).build())
//                                             .toList();
//                        var displaySpecs =
//                                Stream.of("13 inch", "15 inch", "17 inch").map(item -> Specification.builder().name(
//                                        "Màn hình").value(item).build()).toList();
//
//                        List<Specification> list = new ArrayList<>();
//                        List.of(ramSpecs, ssdSpecs, displaySpecs).forEach(list::addAll);
//                        specificationRepository.saveAll(list);
                        
//                        var features = List.of("Di động và tiện lợi", "Kết nối đa dạng", "Pin trâu, hiệu suất mạnh " +
//                                                                                         "mẽ");
                        
//                        List<Product> products = IntStream.rangeClosed(1, 20)
//                                                          .mapToObj(i -> Product.builder()
//                                                                                .name(faker.commerce().productName())
//                                                                                .brand(brands.get(
//                                                                                        number.numberBetween(0,
//                                                                                                             brands.size() - 1)))
//                                                                                .category(categories.get(
//                                                                                        number.numberBetween(0,
//                                                                                                             categories.size() - 1)))
//                                                                                .description(String.join("\n\n", faker.lorem().paragraphs(
//                                                                                        number.numberBetween(2, 4))))
//                                                                                .features(features)
//                                                                                .summary(
//                                                                                        faker.lorem().paragraphs(1).get(0))
//                                                                                .images(getImages())
//                                                                                .slug(faker.code().asin())
//                                                                                .build())
//                                                          .toList();
//                        productRepository.saveAll(products);
//                        Supplier<Set<Specification>> getRandomSpecs = ()->
//                                 Stream.of(ramSpecs, ssdSpecs, displaySpecs).filter(specs -> Math.random() > 0.5)
//                                      .map(specs -> specs.get(number.numberBetween(0, specs.size() -1 )))
//                                      .collect(Collectors.toSet());
//                        ;
                        Function<Product, Stream<Variant>> createVariant = (product)-> IntStream.rangeClosed(1, 10)
                                                                                        .mapToObj(i-> Variant.builder()
                                                                                                             .images(getImages())
                                                                                                             .sku(UUID.randomUUID()
                                                                                                                      .toString())
                                                                                                             .price((double) number.numberBetween(
                                                                                                                     10_000_000,
                                                                                                                     30_000_000))
                                                                                                             .product(
                                                                                                                     product)
                                                                                                             .build());
                    List<Product> products = productRepository.findAll();
                    List<Variant> variants =
                            products.stream().flatMap(createVariant).toList();
                    variantRepository.saveAll(variants);
    
                    List<VariantInventory> inventories =
                            (List<VariantInventory>) IntStream.rangeClosed(0, 10)
                                                              .mapToObj(images -> VariantInventory.builder()
                                                                                                  .createdBy(
                                                                                                          AppUser.builder()
                                                                                                                 .id(1L)
                                                                                                                 .build()))
                                                              .toList();
    
                    //                        var users = authService.findAll().stream().map(userMapper::toUser)
                    //                        .toList();
                    //                        userRepository.saveAll(users);
                    //                        users.forEach(user -> authService.setInitialClaims(user.getId(), user
                    //                        .getFirebaseUid()));
                    //                        List<ShippingAddress> addresses = IntStream.rangeClosed(1, 3)
                    //                                                                   .mapToObj(i ->
                    //                                                                   ShippingAddress.builder()
                    //                                                                                                 .address(
                    //                                                                                                         faker.address()
                    //                                                                                                              .streetAddress())
                    //                                                                                                 .ward(faker.address()
                    //                                                                                                            .city())
                    //                                                                                                 .district(
                    //                                                                                                         faker.address()
                    //                                                                                                              .countryCode())
                    //                                                                                                 .phone(faker.phoneNumber()
                    //                                                                                                             .cellPhone())
                    //                                                                                                 .name(faker.name()
                    //                                                                                                            .fullName())
                    //                                                                                                 .province(
                    //                                                                                                         faker.address()
                    //                                                                                                              .country())
                    //                                                                                                 .user(users.get(0))
                    //                                                                                                 .build())
                    //                                                                   .toList();
                    //                        shippingAddressRepository.saveAll(addresses);
                    System.exit(0);
                };
        }
    
    @PersistenceContext
    EntityManager entityManager;
    private final ProductService productService;
    
//    @PostConstruct
    //        @Transactional
    public void generateSKUForVariant() {
        //                                var graph  =entityManager.createEntityGraph(Variant.class);
        //                                graph.addAttributeNodes("specifications");
        
        var duplicateVariant = new ArrayList<Variant>();
        var graph            = entityManager.createEntityGraph(Product.class);
        //                                entityManager.createQuery("SELECT p FROM Product p JOIN FETCH p.variants JOIN
        //                                Variant v ON p.id = " +
        //                                                          "v.product.id JOIN FETCH v.specifications")
        EntityGraph<Product> entityGraph = entityManager.createEntityGraph(Product.class);
        entityGraph.addSubgraph("variants").addSubgraph("specifications");
        
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", entityGraph);
        
        String jpql =
                "SELECT p FROM Product p JOIN FETCH p.variants v JOIN FETCH v.specifications";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setHint("jakarta.persistence.fetchgraph", entityGraph);
        
        List<Product> list = query.getResultList();
        //                               List<Variant> list = entityManager.createQuery("SELECT v FROM Variant v JOIN
        //                               FETCH v" +
        //                                                                     ".specifications").getResultList();
        List<Variant> duplicateList = new ArrayList<>();
        
        //        try {
        //            var distinct  = new HashSet<>();
        //            var duplicate = new HashSet<String>();
        list.forEach(variantService::generateSku);
        ////            variantService.generateSku(list.get(0));
        list.forEach(product -> {
                         product.getVariants().forEach(variant -> {
                             try {
                                 variantRepository.save(variant);
                             } catch (Exception e) {
                                 log.debug("");
                             }
                         });
        });
    
                         //            });
                         //                                     list.stream().flatMap(product -> product.getVariants().stream())
                         //                                     .forEach(
                         //                                             item -> {
                         //                                                     if(distinct.contains(item.getSku())){
                         //                                                           duplicateList.add(item);
                         //                                                           return;
                         //                                                     }
                         //                                                     distinct.add(item.getSku());
                         //                                             }
                         //                                     );
    
                         //                                     duplicateList.size();
                         //            variantRepository.saveAll(list.stream().flatMap(product -> product.getVariants().stream())
                         //            .toList());
                         //                var duplicateVariant = new ArrayList<Variant>();
                             //                var graph            = entityManager.createEntityGraph(Product.class);
        //                entityManager.createQuery("SELECT p FROM Product p JOIN FETCH p.variants JOIN
        //                Variant v ON p.id = " +
        //                                          "v.product.id JOIN FETCH v.specifications")
        
        //
        //                variantRepository.deleteAll(duplicateVariant);
        
    }
    
    //    @PostConstruct
    public void cleanProduct() {
        EntityGraph<Product> entityGraph = entityManager.createEntityGraph(Product.class);
        entityGraph.addSubgraph("variants").addSubgraph("specifications");
        
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", entityGraph);
        
        String jpql =
                "SELECT p FROM Product p JOIN FETCH p.variants v JOIN FETCH v.specifications";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setHint("jakarta.persistence.fetchgraph", entityGraph);
        
        List<Product>   list                   = query.getResultList();
        var             duplicateVariants      = new ArrayList<Variant>();
        List<Variant[]> duplicateArrayVariants = new ArrayList<>();
        list.stream()
            .forEach(product -> {
                var distinctVariants = new HashSet<Variant>();
                var names =
                        productService.getProductSpecifications(product.getId()).stream().map(
                                ProductSpecificationDTO::name).toList();
                ((Product) product).getVariants()
                                   .forEach(variant -> {
                                       try {
                                           var specMap =
                                                   variant.getSpecifications()
                                                          .stream()
                                                          .distinct()
                                                          .collect(Collectors.toMap(
                                                                  Specification::getName,
                                                                  Specification::getValue));
                    
                                           var result = distinctVariants
                                                                .stream()
                                                                .filter(distinct ->
                                    
                                                                                //                                                                                  var distinctSpec =
                                                                                //                                                                                          distinct.getSpecifications().stream().collect(Collectors.toMap(Specification::getName, Specification::getValue));
                                                                                //                                                                                  names.forEach(name ->{
                                                                                //                                                                                      if()
                                                                                //                                                                                  });
                                                                                distinct.getSpecifications()
                                                                                        .containsAll(
                                                                                                variant.getSpecifications())
                                                                        //                                                                                                  .stream()
                                                                        //                                                                                                  .allMatch(
                                                                        //                                                                                                          specification -> specification.getValue()
                                                                        //                                                                                                                                        .equalsIgnoreCase(
                                                                        //                                                                                                                                                specMap.get(
                                                                        //                                                                                                                                                        specification.getValue()))
                                                                ).findFirst();
                                           if (result.isEmpty()) {
                                               distinctVariants.add(variant);
                                           } else {
                                               duplicateArrayVariants.add(new Variant[] {variant, result.get()});
                                               //                                                   duplicateVariants
                                               //                                                   .add(variant);
                                           }
                                       } catch (Exception e) {
                                           duplicateVariants.add(variant);
                                       }
                                   });
            });
        duplicateArrayVariants.size();
        variantRepository.deleteAll(duplicateVariants);
    }
    //        @GetMapping ("/api/test")
    //        public ResponseEntity<?> removeImage(@RequestParam("email") String email){
    //                OrderRequest.builder()
    //                            .mail(email)
    //                        .orderId(1L)
    //                        .name()
    //
    //                return ResponseEntity.of(Optional.ofNullable(reportRepository.topSpentCustomer()));
    //        }
}
