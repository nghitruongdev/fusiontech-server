package com.vnco.fusiontech.order.web.rest.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.vnco.fusiontech.order.OrderModuleConfiguration.FRIEND_EMAIL;

@Controller
public class OrderSuccessEmailTemplate {
    @Autowired
    public JavaMailSender emailSender;
    @ResponseBody
    @RequestMapping("/sendEmail")
    public String sendEmail()throws MessagingException, IOException {

        // Tạo một đối tượng MimeMessage
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

        // Thiết lập các thông tin cần thiết
        helper.setTo(FRIEND_EMAIL);
        helper.setSubject("Order success");

        // Đọc nội dung của file HTML từ tệp trong thư mục resources
        ClassPathResource htmlResource = new ClassPathResource("email-template.html");
        byte[] htmlBytes = htmlResource.getInputStream().readAllBytes();
        String htmlContent = new String(htmlBytes, StandardCharsets.UTF_8);

        helper.setText(htmlContent, true);

        // Gửi email
        emailSender.send(message);

        return "Email sent to " + FRIEND_EMAIL;
    }
}
