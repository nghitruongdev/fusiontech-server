package com.vnco.fusiontech.common.web.request.mail;

import com.vnco.fusiontech.common.constant.MailTemplate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@Accessors(fluent = true , chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MailRequest {
    @NotNull@Email
    protected String mail;
    protected String subject;
    protected String body;
    protected MailTemplate template;
    
}
