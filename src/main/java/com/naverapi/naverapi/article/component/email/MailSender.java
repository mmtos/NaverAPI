package com.naverapi.naverapi.article.component.email;

import com.naverapi.naverapi.article.domain.email.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Slf4j
@RequiredArgsConstructor
public class MailSender {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMailHtml(Email email) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo( email.getAddress());
            mimeMessageHelper.setSubject(email.getTitle());
            mimeMessageHelper.setText( email.getMessage(), true);

            mailSender.send(mimeMessage);
            log.info("msg send : " + mimeMessage.getMessageID());
        } catch ( MessagingException e ) {
            log.info(e.getMessage());
        }
    }
}
