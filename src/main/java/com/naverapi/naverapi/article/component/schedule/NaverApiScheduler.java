package com.naverapi.naverapi.article.component.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.naverapi.naverapi.article.application.service.NaverApiService;
import com.naverapi.naverapi.article.application.service.NotificationService;
import com.naverapi.naverapi.article.application.service.event.EmailEventWorker;
import com.naverapi.naverapi.article.component.event.EventPublisher;
import com.naverapi.naverapi.article.domain.email.*;
import com.naverapi.naverapi.article.domain.event.EmailEventQueue;
import com.naverapi.naverapi.keyword.application.KeywordService;
import com.naverapi.naverapi.keyword.domain.KeyWord;
import com.naverapi.naverapi.keyword.domain.KeyWordRepository;
import com.naverapi.naverapi.keyword.ui.dto.KeyWordResponseDto;
import com.naverapi.naverapi.user.application.service.UserService;
import com.naverapi.naverapi.user.domain.Role;
import com.naverapi.naverapi.user.domain.User;
import com.naverapi.naverapi.user.domain.UserRepository;
import com.naverapi.naverapi.user.ui.contorller.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverApiScheduler {

    @Autowired
    NaverApiService naverApiService;

    @Autowired
    private EventPublisher publisher;
    private final EmailEventQueue eventQueue;
    private final EmailRepository emailRepository;
    private final NotificationService notificationService;

    private final UserService userService;

    private final KeyWordRepository keyWordRepository;

    @Scheduled( cron = "0 */1 * * * *")
    public void NaverApiScheduler() throws InterruptedException {
//        List<KeyWord>  list = keyWordRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//        for ( KeyWord key : list) {
//            log.info(key.getKeyword() + String.valueOf(key.getUserId()));
//        }
    }

    @Scheduled( cron = "0 */1 * * * *")
    public void getAllUserTest() throws InterruptedException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        // 모든 유저를 조회한다.
        List<UserResponseDto> userResponseDtoList = userService.findAllDesc();
        for ( UserResponseDto dto : userResponseDtoList) {
            log.info(mapper.writeValueAsString(dto));

            Email email = Email.builder()
                            .address(dto.getEmail())
                            .title("test")
                            .message("테스트입니다.")
                            .build();
            User testUser = dto.toEntity();
            testUser.addEmail(email);
            email.setUser(testUser);

            publisher.publish(EmailEvent.of(EmailStatus.STANDBY, EmailType.BLOG, "test", email));
        }
        // 조회가 필요한 키워드 set을 만든다.
        List<KeyWord>  keyList = keyWordRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        // 해당 키워드로 api 조회를 완료한다. -> 조회의 결과를 return 받는 것이 아니라,  조회 결과는 DB에 저장이 된다.
        for ( KeyWord key : keyList) {
            naverApiService.getBlogContentsSortByDate(key.getKeyword());
        }
        // 조회한 결과를 email로 발송한다.
    }

    @Scheduled(fixedRate = 100)
    public void schedule() {
        new EmailEventWorker( eventQueue, emailRepository, notificationService )
                .run();
    }

}
