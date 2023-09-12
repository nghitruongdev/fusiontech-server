package com.vnco.fusiontech.product.web.rest.controller;

import com.vnco.fusiontech.product.repository.StatisticalRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/statistical")
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
            @RequestParam(name = "size", required = false, defaultValue = "10") Optional<Integer> size) {
        var ok = repository.getBestSellerOfYear(startDate, endDate, size.orElse(5));
        return ResponseEntity.ok(ok);
    }

    @GetMapping("/revenue/all")
    public ResponseEntity<?> getRevenueAllYear() {
        var ok = repository.getRevenueAllYear();
        return ResponseEntity.ok(ok);
    }
    @GetMapping("/revenue/day")
    public ResponseEntity<?> getRevenueByDay(@RequestParam(name = "currentDate", required = false) LocalDate currentDate) {
        if (currentDate == null) {
            currentDate = LocalDate.now();
        }
        var ok = repository.getRevenueByDay(currentDate);
        return ResponseEntity.ok(ok);
    }
    @GetMapping("/best-customer")
    public ResponseEntity<?> getBestCustomer(@RequestParam(name = "size", defaultValue = "5") Integer size) {
        var ok = repository.getBestCustomer(size);
        return ResponseEntity.ok(ok);
    }
    @GetMapping("/inventories")
    public ResponseEntity<?> getAvailableInventory() {
        var ok = repository.getAvailableInventory();
        return ResponseEntity.ok(ok);
    }
}
