package com.Gradly.opportunity_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "events")
public class Event {

    @Id
    private String id;

    private String title;
    private String description;
    private String location;

    private LocalDateTime eventDate;

    private String createdBy;
    private String creatorName;


    private LocalDateTime createdAt;
}