package com.Gradly.notification_service.service;

import com.Gradly.notification_service.config.RabbitMQConfig;
import com.Gradly.notification_service.dto.EventNotificationMessage;
import com.Gradly.notification_service.model.Notification;
import com.Gradly.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventNotificationListener {

    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = "event.notifications")
    public void handleEvent(EventNotificationMessage message) {

        Notification notification = new Notification();

        notification.setUserId(message.getCreatedBy());
        notification.setCreatorName(message.getCreatorName());
        notification.setEventTitle(message.getTitle());

        notification.setMessage("created a new event");

        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        System.out.println("Notification created for event: " + message.getTitle());
    }
}