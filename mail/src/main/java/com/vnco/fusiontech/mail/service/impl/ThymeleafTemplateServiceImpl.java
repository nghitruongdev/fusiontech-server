package com.vnco.fusiontech.mail.service.impl;

import com.vnco.fusiontech.common.web.request.mail.MailRequest;
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
        final Context context = new Context();
        context.setVariable("name", "ReallllToo");
        context.setVariable("project_name", "spring-email-with-thymeleaf Demo");
        return templateEngine.process(template.getName(), context);
    }
}
