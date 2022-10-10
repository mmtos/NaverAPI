package com.naverapi.naverapi.article.application.service.notification;

import com.naverapi.naverapi.article.component.email.MailSenderByGoogle;
import com.naverapi.naverapi.article.domain.email.Email;
import com.naverapi.naverapi.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private static final String EXAMPLE_LINK_TEMPLATE = "mail/mail";
    private final TemplateEngine templateEngine;
    private final MailSenderByGoogle mailSenderByGoogle;

    public String sendNotificationByEmail( User user ) {

        Context context = getContext( user.getName() );
        String message = templateEngine.process(EXAMPLE_LINK_TEMPLATE, context);

        return mailSenderByGoogle.sendMailHtml(   Email.builder()
                                        .address(user.getEmail())
                                        .title("test")
                                        .message(message)
                                        .build()   );
    }


    private Context getContext( String name ) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("message", "블로그 추천 콘텐츠입니다.");
        return context;
    }
}
