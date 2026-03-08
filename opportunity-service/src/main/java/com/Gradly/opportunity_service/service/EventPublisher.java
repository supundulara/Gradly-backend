package com.Gradly.opportunity_service.service;

import com.Gradly.opportunity_service.config.RabbitMQConfig;
import com.Gradly.opportunity_service.dto.EventNotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishEvent(EventNotificationMessage message) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EVENT_QUEUE,
                message
        );
    }
}
