package com.vnco.fusiontech.order.web.rest.controller;

import com.vnco.fusiontech.order.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@RestController
public class EmailController {
    @Autowired
    MailService mailService;

    @RequestMapping("/guimail")
    public void sendMail() {
        mailService.sendMail();
    }
}
