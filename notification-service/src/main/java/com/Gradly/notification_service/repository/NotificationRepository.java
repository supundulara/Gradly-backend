package com.Gradly.notification_service.repository;

import com.Gradly.notification_service.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findByUserId(String userId);
}
