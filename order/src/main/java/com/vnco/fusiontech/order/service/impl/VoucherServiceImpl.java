package com.vnco.fusiontech.order.service.impl;

import com.vnco.fusiontech.order.repository.VoucherRepository;
import com.vnco.fusiontech.order.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository repository;


}
