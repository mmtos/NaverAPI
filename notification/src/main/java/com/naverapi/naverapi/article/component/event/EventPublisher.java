package com.naverapi.naverapi.article.component.event;

import com.naverapi.naverapi.article.domain.email.EmailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {
    private final ApplicationEventPublisher publisher;

    public void publish(EmailEvent email){
        publisher.publishEvent(email);
    }
}
