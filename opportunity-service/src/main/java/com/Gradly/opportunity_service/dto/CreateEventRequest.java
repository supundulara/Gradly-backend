package com.Gradly.opportunity_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateEventRequest {

    private String title;
    private String description;
    private String location;
    private LocalDateTime eventDate;
}
