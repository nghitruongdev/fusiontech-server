package com.vnco.fusiontech.mail.service.impl;

import com.vnco.fusiontech.common.constant.MailTemplate;
import com.vnco.fusiontech.mail.service.MailService;
import com.vnco.fusiontech.mail.service.TemplateService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final TemplateService thymeleafService;

    private final JavaMailSender emailSender;
    
    @SneakyThrows
    @Override
    public void sendMail(String emailAddress, String subject, MailTemplate template){
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
        // Thiết lập các thông tin cần thiết
        helper.setTo(emailAddress);
        helper.setSubject(subject);
        // Đọc nội dung của file HTML từ tệp trong thư mục resources
        helper.setText(thymeleafService.getContent(template), true);
        emailSender.send(message);
    }
    
}
