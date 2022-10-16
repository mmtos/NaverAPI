package com.naverapi.naverapi.notification.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.naverapi.naverapi.keyword.domain.KeyWord;
import com.naverapi.naverapi.keyword.domain.KeyWordRepository;
import com.naverapi.naverapi.notification.application.ArticleRequestService;
import com.naverapi.naverapi.notification.application.EmailService;
import com.naverapi.naverapi.notification.application.NotificationService;
import com.naverapi.naverapi.notification.domain.email.Email;
import com.naverapi.naverapi.notification.domain.email.EmailEvent;
import com.naverapi.naverapi.notification.domain.email.EmailStatus;
import com.naverapi.naverapi.notification.domain.email.EmailType;
import com.naverapi.naverapi.notification.domain.email.MailContent;
import com.naverapi.naverapi.notification.event.EmailEventPublisher;
import com.naverapi.naverapi.notification.event.EmailEventQueue;
import com.naverapi.naverapi.notification.event.EmailEventWorker;
import com.naverapi.naverapi.notification.schedule.dto.BlogArticleDto;
import com.naverapi.naverapi.notification.schedule.dto.CafeArticleDto;
import com.naverapi.naverapi.notification.schedule.dto.NewsArticleDto;
import com.naverapi.naverapi.user.application.service.UserService;
import com.naverapi.naverapi.user.ui.contorller.dto.UserResponseDto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverApiScheduler {

    @Autowired
    private EmailEventPublisher publisher;
    private final EmailEventQueue eventQueue;
    private final EmailService emailService;
    private final NotificationService notificationService;
    private final UserService userService;

    private final KeyWordRepository keyWordRepository;

    private final ArticleRequestService articleRequestService;

    @Scheduled( cron = "0 */59 * * * *")
    public void getAllUserAndSendEmail() throws InterruptedException, JsonProcessingException {
        // 모든 유저를 조회한다.
        List<BlogArticleDto> bList = new ArrayList<>();
        List<CafeArticleDto> cList = new ArrayList<>();
        List<NewsArticleDto> nList = new ArrayList<>();

        List<UserResponseDto> userResponseDtoList = userService.findAllDesc();
        for ( UserResponseDto dto : userResponseDtoList) {
            List<KeyWord> keyWordList = keyWordRepository.findByUserId(dto.getId());
            Iterator<KeyWord> iter = keyWordList.iterator();

            while(iter.hasNext()) {
                KeyWord key = iter.next();
                bList.addAll(articleRequestService.getBlogArticleListForSendEmail(key.getKeyword()));
                cList.addAll(articleRequestService.getCafeArticleListForSendEmail(key.getKeyword()));
                nList.addAll(articleRequestService.getNewsArticleListForSendEmail(key.getKeyword()));
            }

            MailContent mailContent = MailContent.builder()
                    .user(dto.toEntity())
                    .bList(bList)
                    .cList(cList)
                    .nList(nList)
                    .build();

            // 해당 유저 이메일 보낸다.
            Email email = Email.builder()
                            .address(dto.getEmail())
                            .title("test")
                            .message("테스트입니다.")
                            .build();
            // 이메일 이벤트 생성
            publisher.publish(EmailEvent.of(EmailStatus.STANDBY, EmailType.BLOG, "test", email, mailContent));
        }
    }

    @Async("taskScheduler")
    @Scheduled(fixedRate = 100)
    public void schedule() {
        new EmailEventWorker( eventQueue, emailService, notificationService )
                .run();
    }

}
