package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.common.utils.DateUtils;
import com.vnco.fusiontech.product.entity.Statistical;
import com.vnco.fusiontech.product.repository.StatisticalRepository;
import com.vnco.fusiontech.product.service.StatisticalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/statistical")
@RequiredArgsConstructor
public class StatisticalController {
    private final StatisticalRepository repository;

    @GetMapping("/revenue")
    public ResponseEntity<?> getDoanhThu() {
        var ok = repository.getRevenue();
        return ResponseEntity.ok(ok);
    }

    @GetMapping("/best-seller")
    public ResponseEntity<?> getBestSellerOfYear(
            @RequestParam(name = "startDate") LocalDate startDate,
            @RequestParam(name = "endDate") LocalDate endDate,
            @RequestParam(name = "size") int size) {

        var ok = repository.getBestSellerOfYear(startDate, endDate, size);
        return ResponseEntity.ok(ok);
    }
}
