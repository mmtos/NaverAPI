package com.naverapi.naverapi.article.service;

import com.naverapi.naverapi.article.application.service.NotificationService;
import com.naverapi.naverapi.article.component.event.EventPublisher;
import com.naverapi.naverapi.article.domain.email.Email;
import com.naverapi.naverapi.article.domain.email.EmailEvent;
import com.naverapi.naverapi.article.domain.email.EmailType;
import com.naverapi.naverapi.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EventPublisher publisher;

    @Test
    void test1(){

        for (int i = 0; i < 3; i++) {
            long startTime = System.currentTimeMillis();

            String result = notificationService.sendNotificationByEmail(User.builder()
                    .name("Terry" + String.valueOf(i) + "Test")
                    .email("terryakishin0814@gmail.com")
                    .build());

            long finishTime = System.currentTimeMillis();
            long elapsedTime = finishTime - startTime;
            System.out.println(result);
            System.out.println(elapsedTime);
        }

    }

    @Test
    void test2() {


        User u = User.builder()
                .name("Terry")
                .email("terryakishin0814@gmail.com")
                .build();

        Email e = Email.builder()
                .address(u.getEmail())
                .title("test proc ")
                .message(" 테스트  거등여 ")
                .build();

        u.addEmail(e);
        e.setUser(u);

        EmailEvent event = EmailEvent.create(EmailType.BLOG, "test", e);

        publisher.publish(event);
    }
}
