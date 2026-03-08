package com.Gradly.opportunity_service.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EventNotificationMessage implements Serializable {

    private String eventId;
    private String title;
    private String createdBy;
}