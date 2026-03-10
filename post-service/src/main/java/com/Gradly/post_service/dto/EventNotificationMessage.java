package com.Gradly.post_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventNotificationMessage {

    private String type;

    private String postId;

    private String creatorId;
    private String creatorName;

    private String actorId;
    private String actorName;
}