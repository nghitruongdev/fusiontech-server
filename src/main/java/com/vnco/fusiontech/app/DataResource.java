package com.vnco.fusiontech.app;

import com.vnco.fusiontech.common.constant.DBConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Getter
@Slf4j
public class DataResource {
    private @Value ("classpath:data.orig/BRAND.csv")                 Resource brand;
    private @Value ("classpath:data.orig/CATEGORY.csv")              Resource category;
    private @Value ("classpath:data.orig/PRODUCT.csv")               Resource product;
    private @Value ("classpath:data.orig/APP_USER.csv")              Resource user;
    private @Value ("classpath:data.orig/APP_ORDER.csv")             Resource order;
    private @Value ("classpath:data.orig/PAYMENT.csv")               Resource payment;
    private @Value ("classpath:data.orig/REVIEW.csv")                Resource review;
    private @Value ("classpath:data.orig/PRODUCT_SPECIFICATION.csv") Resource specification;
    private @Value ("classpath:data.orig/PRODUCT_VARIANT.csv")       Resource variant;
    private @Value ("classpath:data.orig/SHIPPING_ADDRESS.csv")      Resource address;
    private @Value ("classpath:data.orig/VARIANT_SPECIFICATION.csv") Resource variantSpecification;
    private @Value ("classpath:data.orig/VOUCHER.csv")               Resource voucher;
    private @Value ("classpath:data.orig/data.sql")                  Resource sqlData;
    
    private final JdbcTemplate jdbcTemplate;
    
    
//    @PostConstruct
    public void loadCsvData() throws IOException {
        Map<String, Resource> map = new HashMap<>();
        map.put(DBConstant.USER_TABLE, user);
        String sql = """
                     LOAD DATA INFILE '%s' INTO TABLE %s
                     FIELDS TERMINATED BY ','
                     ENCLOSED BY '"'
                     LINES TERMINATED BY '\\n'
                     IGNORE 1 LINES;
                     """;
        map.forEach((name, data) -> {
            try {
                jdbcTemplate.execute(sql.formatted(data.getFile().getAbsolutePath(), name));
                log.debug("Done loaded data.orig from " + data.getFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
