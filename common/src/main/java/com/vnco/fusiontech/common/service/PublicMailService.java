package com.vnco.fusiontech.common.service;


import com.vnco.fusiontech.common.web.request.mail.MailRequest;

public interface PublicMailService {
     void sendMail(MailRequest mailRequest);
}
