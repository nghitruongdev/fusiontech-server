package com.vnco.fusiontech.product.service;

import com.vnco.fusiontech.common.service.PublicVoucherService;
import com.vnco.fusiontech.product.web.rest.request.VoucherRequest;

public interface VoucherService extends PublicVoucherService {
    String create();

    String manuallyCreate(VoucherRequest createVoucherRequest);

    String delete(Long id);

    String update(Long id, VoucherRequest createVoucherRequest);
}
