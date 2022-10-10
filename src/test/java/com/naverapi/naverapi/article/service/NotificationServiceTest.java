package com.naverapi.naverapi.article.service;

import com.naverapi.naverapi.article.application.service.apirequest.NaverApiRequestService;
import com.naverapi.naverapi.article.application.service.article.ArticleService;
import com.naverapi.naverapi.article.application.service.notification.NotificationService;
import com.naverapi.naverapi.article.component.event.EventPublisher;
import com.naverapi.naverapi.article.domain.blogarticle.NaverBlogDateResult;
import com.naverapi.naverapi.article.ui.dto.ApiResponseSaveDto;
import com.naverapi.naverapi.article.ui.dto.NaverBlogArticleResponseDto;
import com.naverapi.naverapi.article.ui.dto.NaverBlogResultSaveDto;
import com.naverapi.naverapi.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootTest
public class NotificationServiceTest {

    private final static int TYPE_BLOG = 1;
    private final static boolean TYPE_DATE = false;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EventPublisher publisher;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NaverApiRequestService naverApiRequestService;

    @Test
    void test2(){

        naverApiRequestService.getBlogContentsSortByDate("여행");
        List<NaverBlogArticleResponseDto> list1 = articleService.getRowBlogArticle(100);
        List<NaverBlogArticleResponseDto> list2 = articleService.getRowBlogArticle(200);
//
        Set<NaverBlogArticleResponseDto> set1 = new HashSet<>(list1);
        Set<NaverBlogArticleResponseDto> set2 = new HashSet<>(list2);
//
        set2.removeAll(set1);
//
        System.out.println(set2.size());
    }

    @Test
    void test1(){

//        for (int i = 0; i < 3; i++) {
//            long startTime = System.currentTimeMillis();
//
//            String result = notificationService.sendNotificationByEmail(User.builder()
//                    .name("Terry" + String.valueOf(i) + "Test")
//                    .email("terryakishin0814@gmail.com")
//                    .build());
//
//            long finishTime = System.currentTimeMillis();
//            long elapsedTime = finishTime - startTime;
//            System.out.println(result);
//            System.out.println(elapsedTime);
//        }

    }
}
