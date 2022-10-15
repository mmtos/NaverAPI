package com.naverapi.naverapi.article.domain.event;
import com.naverapi.naverapi.article.domain.email.EmailEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class EmailEventQueue {
    private final Queue<EmailEvent> queue;
    private final int queueSize;


    public EmailEventQueue(int queueSize) {
        this.queue = new LinkedBlockingQueue<>(queueSize);
        this.queueSize = queueSize;
    }

    public static EmailEventQueue of(int size){
        return new EmailEventQueue(size);
    }

    public boolean offer(EmailEvent email) {
        boolean returnValue = queue.offer(email);
        healthCheck();
        return returnValue;
    }

    public EmailEvent poll() {
        if(queue.size() <= 0) {
            throw new IllegalStateException("No events in the queue!");
        }
        EmailEvent email = queue.poll();
        healthCheck();
        return email;
    }

    public  boolean isFull(){
        return size() == queueSize;
    }

    public boolean isRemaining() {
        return size() > 0;
    }

    private int size() {
        return queue.size();
    }

    private void healthCheck(){
        log.info("{\"totalQueueSize\":{}, \"currentQueueSize\":{}}", queueSize, size());
    }
}
