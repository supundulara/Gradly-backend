package com.Gradly.opportunity_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EventResponse {

    private String id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime eventDate;

    private String createdBy;
    private String creatorName;

    private LocalDateTime createdAt;
}
