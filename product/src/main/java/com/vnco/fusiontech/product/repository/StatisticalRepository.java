package com.vnco.fusiontech.product.repository;


import java.time.LocalDate;
import java.util.List;

public interface StatisticalRepository {
    List<Object> getRevenue();
    List<Object> getBestSellerOfYear(LocalDate startDate, LocalDate endDate, Integer size);

    List<Object> getDiscountProducts();

}
