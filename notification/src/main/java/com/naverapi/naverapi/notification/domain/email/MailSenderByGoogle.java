package com.naverapi.naverapi.notification.domain.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MailSenderByGoogle {
    @Autowired
    private JavaMailSender mailSender;

    public String sendMailHtml(Email email) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo( email.getAddress() );
            mimeMessageHelper.setSubject(email.getTitle() );
            mimeMessageHelper.setText( email.getMessage(), true);

            mailSender.send( mimeMessage );
            log.info("msg send : " + mimeMessage.getMessageID());
            return "success";
        } catch ( MessagingException e ) {
            log.info(e.getMessage());
            return e.getMessage();
        }
    }
}