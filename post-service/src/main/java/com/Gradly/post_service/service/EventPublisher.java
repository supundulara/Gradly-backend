package com.Gradly.post_service.service;


import com.Gradly.post_service.dto.EventNotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    private static final String QUEUE = "event.notifications";

    public void publishEvent(EventNotificationMessage message){
        rabbitTemplate.convertAndSend(QUEUE, message);
    }
}
