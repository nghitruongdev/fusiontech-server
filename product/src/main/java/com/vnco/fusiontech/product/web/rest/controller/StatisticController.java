package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.repository.StatisticalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/stat")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticalRepository repository;

    @GetMapping("/revenue")
    public ResponseEntity<?> getDoanhThu() {
        var ok = repository.getRevenue();
        return ResponseEntity.ok(ok);
    }

    @GetMapping("/best-seller")
    public ResponseEntity<?> getBestSellerOfYear(
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) LocalDate endDate,
            @RequestParam(name = "size", required = false) Optional<Integer> size) {
        var ok = repository.getBestSellerOfYear(startDate, endDate, size.orElse(5));
        return ResponseEntity.ok(ok);
    }
}
