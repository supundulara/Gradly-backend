package com.Gradly.messaging_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "messages")
@Data
public class Message {

    @Id
    private String id;

    private String conversationId;

    private String senderId;

    private String senderName;

    private String content;

    private LocalDateTime createdAt;
}
