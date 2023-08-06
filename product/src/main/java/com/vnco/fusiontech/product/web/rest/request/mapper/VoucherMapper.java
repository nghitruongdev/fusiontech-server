package com.vnco.fusiontech.product.web.rest.request.mapper;

import com.vnco.fusiontech.product.entity.Voucher;
import com.vnco.fusiontech.product.web.rest.request.VoucherRequest;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface VoucherMapper {
    @Mapping(
            target = "id", ignore = true
    )
    Voucher toVoucher(VoucherRequest createVoucherRequest);

    void particleUpdate(VoucherRequest createVoucherRequest, @MappingTarget Voucher voucher);
}
