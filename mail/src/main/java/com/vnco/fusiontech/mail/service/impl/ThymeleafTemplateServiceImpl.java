package com.vnco.fusiontech.mail.service.impl;

import com.vnco.fusiontech.common.web.request.mail.MailRequest;
import com.vnco.fusiontech.common.web.request.mail.OrderRequest;
import com.vnco.fusiontech.mail.service.TemplateService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class ThymeleafTemplateServiceImpl implements TemplateService {
    private final TemplateEngine templateEngine;
    
    @Override
    public String getContent(@NonNull MailRequest request) {
        var template = request.template();
        if(request instanceof OrderRequest orderRequest){
            return getOrderContent(orderRequest);
        }
        final Context context = new Context();
        context.setVariable("name", "ReallllToo");
        context.setVariable("project_name", "spring-email-with-thymeleaf Demo");
        return templateEngine.process(template.getName(), context);
    }
    
    private String getOrderContent(OrderRequest request){
        final Context context = new Context();
        context.setVariable("request", request);
        //        context.setVariable("orderId", request.orderId());
        //        context.setVariable("name", request.name());
        //        context.setVariable("phoneNumber", request.phoneNumber());
        //        context.setVariable("date", request.date());
        //        context.setVariable("orderTotal", request.orderTotal());
        //        context.setVariable("subtotal", request.subtotal());
        //        context.setVariable("", request.);
        //        context.setVariable("productImageUrl",request.productImageUrl());
        return templateEngine.process(request.template().getName(), context);
    }
}
