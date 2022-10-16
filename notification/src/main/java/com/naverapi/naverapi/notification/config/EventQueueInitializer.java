package com.naverapi.naverapi.notification.config;

import com.naverapi.naverapi.notification.event.EmailEventQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventQueueInitializer {
    @Bean
    public EmailEventQueue emailEventQueue(){
        return EmailEventQueue.of(1_100);
    }
}
