package com.Gradly.post_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private String id;
    private String postId;
    private String userId;
    private String userName;
    private String content;
    private LocalDateTime createdAt;

}
