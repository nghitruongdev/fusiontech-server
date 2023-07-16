package com.vnco.fusiontech.mail.service;

import com.vnco.fusiontech.common.constant.MailTemplate;

public interface MailService {
    void sendMail(String emailAddress, String subject, MailTemplate template);
}
