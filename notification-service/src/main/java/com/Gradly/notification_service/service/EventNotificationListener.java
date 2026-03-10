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

        notification.setUserId(message.getCreatorId());
        notification.setEventTitle(message.getEventTitle());

        if ("EVENT_CREATED".equals(message.getType())) {

            notification.setActorName(message.getCreatorName());
            notification.setCreatorName(message.getCreatorName());
            notification.setMessage("created a new event");

        }
        else if ("EVENT_RSVP".equals(message.getType())) {

            notification.setActorName(message.getAttendeeName());
            notification.setCreatorName(message.getCreatorName());
            notification.setMessage("is attending your event");

        }
        else if ("EVENT_RSVP_CANCELLED".equals(message.getType())) {

            notification.setActorName(message.getAttendeeName());
            notification.setCreatorName(message.getCreatorName());
            notification.setMessage("cancelled attendance for your event");

        }
        else if ("POST_LIKE".equals(message.getType())) {

            notification.setActorName(message.getActorName());
            notification.setCreatorName(message.getCreatorName());
            notification.setMessage("liked your post");

        }
        else if ("POST_COMMENT".equals(message.getType())) {

            notification.setActorName(message.getActorName());
            notification.setCreatorName(message.getCreatorName());
            notification.setMessage("commented on your post");

        }

        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }
}