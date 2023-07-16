package com.vnco.fusiontech.mail.service;

import com.vnco.fusiontech.common.constant.MailTemplate;

public interface TemplateService {
    String getContent(MailTemplate template);
}
