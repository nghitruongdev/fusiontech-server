package com.vnco.fusiontech.order.entity.projection;

import com.vnco.fusiontech.order.entity.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(types = {Voucher.class}, name = "usage-count")
public interface VoucherWithUsageCount {

    Long getId();

    String getCode();

    String getDescription();

    Byte getDiscount();

    Double getMinOrderAmount();

    Double getMaxDiscountAmount();

    LocalDateTime getStartDate();

    LocalDateTime getExpirationDate();

    Integer getLimitUsage();

    Short getUserLimitUsage();

//    @Value("#{target.usage != null ? (target.usage.doubleValue() / target.limitUsage) * 100 : 0}")
//    Double getUsagePercent();
//    @Formula(FORMULA.VOUCHER_USAGE)
//    private Integer usage;
//
//    private Double getUsagePercent(){
//        return usage != null ? ( usage.doubleValue() / limitUsage) * 100 : 0;
//    }

}
