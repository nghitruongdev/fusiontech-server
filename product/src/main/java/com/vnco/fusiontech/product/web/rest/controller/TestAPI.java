package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.entity.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/test")
public class TestAPI {
    @GetMapping
    void getReviewById(Date date) {
        log.debug("gi cung duoc {}", date);
    }
}
