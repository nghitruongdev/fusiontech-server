package com.vnco.fusiontech.product.service.impl;

import com.vnco.fusiontech.product.entity.Voucher;
import com.vnco.fusiontech.product.repository.VoucherRepository;
import com.vnco.fusiontech.product.service.VoucherService;
import com.vnco.fusiontech.product.web.rest.request.VoucherRequest;
import com.vnco.fusiontech.product.web.rest.request.mapper.VoucherMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.function.Function;

@Service
@Slf4j
public class VoucherServiceImpl implements VoucherService {

    final
    VoucherMapper mapper;
    final VoucherRepository repository;


    public VoucherServiceImpl(VoucherRepository repository, VoucherMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public String create() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        int length = 8;

        //perform generate code and save to database
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        String code = sb.toString();
        Voucher voucher = Voucher.builder()
                .code(code)
                .startDate(Instant.now())
                .expirationDate(Instant.now().plus(3, ChronoUnit.DAYS))
                .active(true)
                .build();
        repository.save(voucher);
        return voucher.getCode();
    }

    @Override
    public String manuallyCreate(VoucherRequest createVoucherRequest) {
        Voucher voucher = Voucher.builder()
                .code(createVoucherRequest.code())
                .discount(createVoucherRequest.discount())
                .minOrderAmount(createVoucherRequest.minOrderAmount())
                .maxDiscountAmount(createVoucherRequest.maxDiscountAmount())
                .startDate(createVoucherRequest.startDate())
                .expirationDate(createVoucherRequest.expirationDate())
                .active(true)
                .build();
        repository.save(voucher);
        return voucher.getCode();
    }

    @Override
    public String delete(Long id) {
        repository.deleteById(id);
        return "Voucher deleted" + id;
    }

    @Override
    public String update(Long id, VoucherRequest voucherRequest) {
        var voucher = repository.findById(id).orElseThrow();
        mapper.particleUpdate(voucherRequest, voucher);

        log.info("voucher: {}", voucher.getCode());
        repository.save(voucher);
        return "Updated voucher";
    }

    @Override
    public String getVoucherCode(Long id) {
        var voucher = repository.findById(id).orElseThrow();
        return voucher.getCode();
    }

    @Override
    public boolean isCodeExist(String code) {
        return repository.existsByCode(code);
    }

    @Override
    public Long getVoucherId(String code) {
        var voucher = repository.findByCode(code).orElseThrow();
        return voucher.getId();
    }

    @Override
    public void setActive(Long code) {
        var voucher = repository.findById(code).orElseThrow();
        voucher.setActive(true);
    }

    @Override
    public void setInactive(Long code) {
        var voucher = repository.findById(code).orElseThrow();
        voucher.setActive(false);
    }

    @Override
    public Double getDiscount(String code) {
        var voucher = repository.findByCode(code).orElseThrow();
        return voucher.getDiscount();
    }
}
