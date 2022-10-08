package com.naverapi.naverapi.article.component.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.naverapi.naverapi.article.application.service.NaverApiService;
import com.naverapi.naverapi.article.application.service.NotificationService;
import com.naverapi.naverapi.article.application.service.event.EmailEventWorker;
import com.naverapi.naverapi.article.component.event.EventPublisher;
import com.naverapi.naverapi.article.domain.email.Email;
import com.naverapi.naverapi.article.domain.email.EmailEvent;
import com.naverapi.naverapi.article.domain.email.EmailRepository;
import com.naverapi.naverapi.article.domain.email.EmailType;
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

//    @Autowired
//    NaverApiService naverApiService;

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
        List<UserResponseDto> userResponseDtoList = userService.findAllDesc();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());

        for ( UserResponseDto dto : userResponseDtoList) {
            log.info(mapper.writeValueAsString(dto));
        }
    }

    @Scheduled(fixedRate = 100)
    public void schedule() {
        new EmailEventWorker( eventQueue, emailRepository, notificationService )
                .run();
    }

}
