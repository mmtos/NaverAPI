package com.naverapi.naverapi.article.application.service.event;

import com.naverapi.naverapi.article.application.service.NotificationService;
import com.naverapi.naverapi.article.domain.email.Email;
import com.naverapi.naverapi.article.domain.email.EmailEvent;
import com.naverapi.naverapi.article.domain.email.EmailRepository;
import com.naverapi.naverapi.article.domain.event.EmailEventQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class EmailEventWorker implements Runnable{

    private final EmailEventQueue eventQueue;
    private final EmailRepository emailRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public void run() {
        if(eventQueue.isRemaining()) {
            EmailEvent emailEvent = eventQueue.poll();
            String result  = notificationService.sendNotificationByEmail(emailEvent.getEmail().getUser());
            Email email =  emailRepository.save(emailEvent.getEmail());
            log.info( email.getTitle() + " " + email.getAddress() + " " + email.getMessage() );
            log.info(result);
        }
    }
}
