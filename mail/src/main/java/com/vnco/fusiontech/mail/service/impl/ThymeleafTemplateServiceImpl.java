package com.vnco.fusiontech.mail.service.impl;

import com.vnco.fusiontech.common.constant.MailTemplate;
import com.vnco.fusiontech.mail.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class ThymeleafTemplateServiceImpl implements TemplateService {
    private final TemplateEngine templateEngine;
    
    @Override
    public String getContent(MailTemplate template) {
        final Context context = new Context();
        context.setVariable("name", "ReallllToo");
        context.setVariable("project_name", "spring-email-with-thymeleaf Demo");
        return templateEngine.process(template.getName(), context);
    }
}
