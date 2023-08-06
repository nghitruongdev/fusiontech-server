package com.vnco.fusiontech.common.service;

public interface PublicVoucherService {
    String getVoucherCode(Long id);
    boolean isCodeExist(String code);

    Long getVoucherId(String code);
    void setActive(Long code);
    void setInactive(Long voucherId);

    Double getDiscount(String code);
}
