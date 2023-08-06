package com.vnco.fusiontech.product.web.rest.controller;


import com.vnco.fusiontech.product.service.VoucherService;
import com.vnco.fusiontech.product.web.rest.request.VoucherRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {

    final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/create/auto")
    public ResponseEntity<?> createVoucher() {
        voucherService.create();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create/manual")
    public ResponseEntity<?> createVoucherManually(@RequestBody VoucherRequest createVoucherRequest) {
        voucherService.manuallyCreate(createVoucherRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateVoucher(@RequestBody VoucherRequest voucherRequest,
                                           @PathVariable("id") Long id) {
        voucherService.update(id, voucherRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable("id") Long id) {
        voucherService.delete(id);
        return ResponseEntity.ok().build();
    }
}
