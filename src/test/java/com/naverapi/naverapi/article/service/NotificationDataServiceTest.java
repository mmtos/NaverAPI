package com.naverapi.naverapi.article.service;

import com.naverapi.naverapi.article.application.service.apirequest.NaverApiRequestService;
import com.naverapi.naverapi.article.application.service.article.ArticleService;
import com.naverapi.naverapi.article.application.service.notification.NotificationService;
import com.naverapi.naverapi.article.component.event.EventPublisher;
import com.naverapi.naverapi.article.ui.dto.NaverBlogArticleResponseDto;
import com.naverapi.naverapi.keyword.domain.KeyWord;
import com.naverapi.naverapi.keyword.domain.KeyWordRepository;
import com.naverapi.naverapi.user.application.service.UserService;
import com.naverapi.naverapi.user.domain.Role;
import com.naverapi.naverapi.user.domain.User;
import com.naverapi.naverapi.user.domain.UserRepository;
import com.naverapi.naverapi.user.ui.contorller.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;


@SpringBootTest
public class NotificationDataServiceTest {

    private final static int TYPE_BLOG = 1;
    private final static boolean TYPE_DATE = false;
    @Autowired
    private  NotificationService notificationService;

    @Autowired
    private EventPublisher publisher;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NaverApiRequestService naverApiRequestService;

    @Autowired
    private KeyWordRepository keyWordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void test3(){
//        naverApiRequestService.getBlogContentsSortByDate("수리남");
//        List<NaverBlogArticleResponseDto> bList = articleService.getBlogArticleListForSendEmail("수리남");
//
//        for ( NaverBlogArticleResponseDto dto : bList) {
//            System.out.println(dto.getKeyword() + " " + dto.getBloggerlink());
//        }
    }

    @Test
    void test2(){

        User u = new User("Terry Shin", "terryakishin0814@gmail.com", "test", Role.ADMIN);
        userRepository.save(u);
        List<KeyWord> userKeywords = new ArrayList<>();
        KeyWord k1 = new KeyWord("수리남", u.getId());
        KeyWord k2 = new KeyWord("하정우", u.getId());
        userKeywords.add(k1);
        userKeywords.add(k2);
        keyWordRepository.saveAllAndFlush(userKeywords);

        List<KeyWord> temp = keyWordRepository.findByUserId(u.getId());// new ArrayList<>();
        Iterator<KeyWord> iter = temp.iterator();//userKeywords.iterator();

        while(iter.hasNext()){
            KeyWord key = iter.next();
            System.out.println(key.getKeyword());
//            naverApiRequestService.getBlogContentsSortByDate(key.getKeyword());
//            naverApiRequestService.getCafeContentsSortByDate(key.getKeyword());
//            naverApiRequestService.getNewsContentsSortByDate(key.getKeyword());
        }

//        System.out.println(n.getBlogArticle());
//        System.out.println(n.getCafeArticle());
//        System.out.println(n.getNewsArticle());

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
