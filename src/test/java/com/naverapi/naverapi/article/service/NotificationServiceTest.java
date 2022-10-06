package com.naverapi.naverapi.article.service;

import com.naverapi.naverapi.article.application.service.NotificationService;
import com.naverapi.naverapi.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Test
    void test1(){
        notificationService.sendNotificationByEmail(User.builder()
                .name("jsh")
                .email("jss223kr@gmail.com")
                .build());
    }
}
