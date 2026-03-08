package com.Gradly.post_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostResponse {

    private String id;
    private String authorId;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private long likeCount;
    private long commentCount;
}
