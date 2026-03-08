package com.Gradly.opportunity_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "event_rsvps")
public class EventRSVP {

    @Id
    private String id;

    private String eventId;
    private String userId;

    private LocalDateTime createdAt;
}