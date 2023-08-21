package com.vnco.fusiontech.order.web.rest.controller;

import com.vnco.fusiontech.common.constant.MailTemplate;
import com.vnco.fusiontech.common.service.PublicMailService;
import com.vnco.fusiontech.common.web.request.mail.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MailController {

    private final PublicMailService mailService;

    @GetMapping("/sendmail/test")
    public ResponseEntity<?> sendOrderSuccess() {
        OrderRequest rq = OrderRequest.builder()
                .subject("Order success")
                .mail("nxhao3003@gmail.com")
                .orderId(3L)
                .items(List.of())
                .date(Instant.now())
                .name("Xuân ahor")
                .phone("0373038293")
                .shipping(21D)
                .date(Instant.now())
                .subtotal(123D)
                .address("Truong trung hoc pho thong FPT")
                .paymentStatus("NGAN HANG CHO VAY NANG LAI")
                .orderTotal(300D)
                .template(MailTemplate.ORDER_SUCCESS)
                .build();
        //        OrderRequest rq = new OrderRequest();
        mailService.sendMail(rq);
        log.info("{}", rq);
        return ResponseEntity.ok().build();
    }
}
