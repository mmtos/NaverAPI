package com.naverapi.naverapi.notification.application;

import com.naverapi.naverapi.notification.domain.email.Email;
import com.naverapi.naverapi.notification.domain.email.MailContent;
import com.naverapi.naverapi.notification.domain.email.MailSenderByGoogle;
import java.util.concurrent.CompletableFuture;
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

    public CompletableFuture<String> sendNotificationByEmail(MailContent mailContent) {
        // 메일 발송
        Context context = getContext( mailContent );
        String message = templateEngine.process(EXAMPLE_LINK_TEMPLATE, context);
        String result =  mailSenderByGoogle.sendMailHtml(   Email.builder()
                .address( mailContent.getUser().getEmail() )
                .title("test")
                .message(message)
                .build()   );
        return CompletableFuture.completedFuture(result);
    }



    private Context getContext( MailContent mailContent) {
        Context context = new Context();
        context.setVariable("name", mailContent.getUser().getName());
        context.setVariable("message", "블로그 추천 콘텐츠입니다.");
        context.setVariable("blogList", mailContent.getBList());
        context.setVariable("message2", "카페 추천 콘텐츠입니다.");
        context.setVariable("CafeList", mailContent.getCList());
        context.setVariable("message3", "뉴스 추천 콘텐츠입니다.");
        context.setVariable("NewsList", mailContent.getNList());
        return context;
    }


}
