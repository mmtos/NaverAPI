package com.naverapi.naverapi.notification.event;

import com.naverapi.naverapi.notification.domain.email.EmailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void publish(EmailEvent email){
        publisher.publishEvent(email);
    }
}
