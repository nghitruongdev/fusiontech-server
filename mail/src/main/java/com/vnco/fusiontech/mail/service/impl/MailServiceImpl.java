package com.vnco.fusiontech.mail.service.impl;

import com.vnco.fusiontech.common.web.request.mail.MailRequest;
import com.vnco.fusiontech.mail.service.MailService;
import com.vnco.fusiontech.mail.service.TemplateService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final TemplateService thymeleafService;

    private final JavaMailSender emailSender;
    
    private final Queue<MailRequest> mailRequests = new ArrayDeque<>();
    private final Queue<MimeMessage> messages = new ArrayDeque<>();
    
    @Override
    public void sendMail(MailRequest mailRequest) {
        mailRequests.add(mailRequest);
    }
    
 
    
    @Scheduled (timeUnit = TimeUnit.SECONDS, fixedRate = 5)
    private void createMail(){
        log.debug("There are {} going to be created", mailRequests.size());
    
        while(!mailRequests.isEmpty()){
            messages.add(createMessage(mailRequests.remove()));
        }
    }
    
    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 10)
    private void sendMail(){
        log.debug("There are {} going to be sent", messages.size());
        emailSender.send(messages.toArray(new MimeMessage[]{}));
        messages.clear();
    }
    
    @SneakyThrows
    private MimeMessage createMessage(MailRequest request){
        MimeMessage       message = emailSender.createMimeMessage();
        MimeMessageHelper helper  = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
    
        // Thiết lập các thông tin cần thiết
        helper.setTo(request.mail());
        helper.setSubject(request.subject());
        helper.setText(request.body());
        if(request.template() != null){
            // Đọc nội dung của file HTML từ tệp trong thư mục resources
            helper.setText(thymeleafService.getContent(request), true);
        }
        return message;
    }
}
