package com.vnco.fusiontech.mail.service;

import com.vnco.fusiontech.common.web.request.mail.MailRequest;

public interface TemplateService {
    String getContent(MailRequest request);
}
