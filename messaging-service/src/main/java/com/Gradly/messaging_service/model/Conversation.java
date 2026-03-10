package com.Gradly.messaging_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "conversations")
@Data
public class Conversation {

    @Id
    private String id;

    private List<String> participantIds;

    private List<String> participantNames;

    private String lastMessage;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}