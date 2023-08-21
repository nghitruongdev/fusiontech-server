package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.common.utils.TupleUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class StatisticalRepositoryImpl implements StatisticalRepository {
    @PersistenceContext
    EntityManager manager;

    @Override
    public List<Object> getRevenue() {
        var list = manager.createNativeQuery("""
                        call get_revenue()
                        """, Tuple.class)
                .getResultList();
        return TupleUtils.convertToJsonNode(list);
    }

    @Override
    public List<Object> getBestSellerOfYear(LocalDate startDate, LocalDate endDate, Integer size) {
        var list = manager.createNativeQuery("""
                        call get_best_selling_products(:startDate, :endDate, :size);
                        """, Tuple.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("size", size)
                .getResultList();
        return TupleUtils.convertToJsonNode(list);
    }

    @Override
    public List<Object> getRevenueAllYear() {
        var list = manager.createNativeQuery("""
                        call get_revenue_all_year()
                        """, Tuple.class)
                .getResultList();
        return TupleUtils.convertToJsonNode(list);
    }

    @Override
    public List<Object> getBestCustomer(Integer size) {
        var list = manager.createNativeQuery("""
                        call get_best_customer(:size)
                        """, Tuple.class)
                .setParameter("size", size)
                .getResultList();
        return TupleUtils.convertToJsonNode(list);
    }

    @Override
    public List<Object> getRevenueByDay(LocalDate currentDate) {
        var list = manager.createNativeQuery("""
                        call get_revenue_by_day(:currentDate);
                        """, Tuple.class)
                .setParameter("currentDate", currentDate)
                .getResultList();
        return TupleUtils.convertToJsonNode(list);
    }

    @Override
    public List<Object> getAvailableInventory() {
        var list = manager.createNativeQuery("""
                call get_available_inventory()
                """,Tuple.class)
                .getResultList();
        return TupleUtils.convertToJsonNode(list);
    }


}
