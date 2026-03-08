package com.Gradly.notification_service.dto;

import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class EventNotificationMessage  {

    private String eventId;
    private String title;
    private String createdBy;
    private String creatorName;
}