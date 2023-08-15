package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.product.entity.Statistical;
import com.vnco.fusiontech.product.repository.StatisticalRepository;
import com.vnco.fusiontech.product.service.StatisticalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticalServiceImpl implements StatisticalService {

    final
    StatisticalRepository repository;

    public StatisticalServiceImpl(StatisticalRepository repository) {
        this.repository = repository;
    }

//    @Override
//    public List<Object> calculateDoanhThu() {
//        return repository.getRevenue();
//    }

//    @Override
//    public List<Object> getBestSellOfYear() {
//        return repository.getBestSellerOfYear();
//    }


}
