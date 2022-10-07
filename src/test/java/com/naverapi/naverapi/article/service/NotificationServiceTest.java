package com.naverapi.naverapi.article.service;

import com.naverapi.naverapi.article.application.service.NotificationService;
import com.naverapi.naverapi.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

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
}
