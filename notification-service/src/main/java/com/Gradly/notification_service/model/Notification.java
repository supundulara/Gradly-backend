package com.Gradly.notification_service.model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;

    private String userId;

    private String actorName;      // person who triggered action
    private String creatorName;    // owner of content

    private String eventTitle;

    private String message;

    private boolean read;

    private LocalDateTime createdAt;
}