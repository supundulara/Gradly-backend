package com.Gradly.notification_service.controller;

import com.Gradly.notification_service.model.Notification;
import com.Gradly.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<Notification> getUserNotifications(
            @RequestHeader("X-User-Id") String userId) {

        return notificationService.getUserNotifications(userId);
    }
}