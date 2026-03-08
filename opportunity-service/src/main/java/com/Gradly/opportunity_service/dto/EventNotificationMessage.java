package com.Gradly.opportunity_service.dto;

import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class EventNotificationMessage {

    private String type;

    private String eventId;
    private String eventTitle;

    private String creatorId;
    private String creatorName;

    private String attendeeId;
    private String attendeeName;
}