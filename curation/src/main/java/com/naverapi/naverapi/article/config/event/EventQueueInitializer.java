package com.naverapi.naverapi.article.config.event;

import com.naverapi.naverapi.article.domain.event.EmailEventQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventQueueInitializer {
    @Bean
    public EmailEventQueue emailEventQueue(){
        return EmailEventQueue.of(1_100);
    }
}
