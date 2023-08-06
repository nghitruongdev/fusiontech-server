package com.vnco.fusiontech.product.repository;

import com.vnco.fusiontech.product.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    boolean existsByCode(String code);
    Optional<Voucher> findByCode(String code);
}
