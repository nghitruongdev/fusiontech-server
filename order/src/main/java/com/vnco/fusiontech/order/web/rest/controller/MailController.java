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

@RestController
@Slf4j
@RequiredArgsConstructor
public class MailController {
    
    private final PublicMailService mailService;
    
    @GetMapping ("/sendmail/test")
    public ResponseEntity<?> sendOrderSuccess() {
        OrderRequest rq = OrderRequest.builder()
                                      .subject("Order success")
                                      .mail("nxhao3003@gmail.com")
                                      .orderId(3L)
                                      .date(Instant.now())
                                      .name("Xuân ahor")
                                      .phone("0373038293")
                                      //                                      .items(List.of(new OrderItemDTO()))
                                      .orderTotal(300D)
                                      .template(MailTemplate.ORDER_SUCCESS)
                                      .build();
        //        OrderRequest rq = new OrderRequest();
        mailService.sendMail(rq);
        log.info("{}", rq);
        return ResponseEntity.ok().build();
    }
}
