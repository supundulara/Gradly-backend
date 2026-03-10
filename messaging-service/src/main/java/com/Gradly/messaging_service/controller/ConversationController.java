package com.Gradly.messaging_service.controller;

import com.Gradly.messaging_service.dto.CreateConversationRequest;
import com.Gradly.messaging_service.model.Conversation;
import com.Gradly.messaging_service.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping
    public Conversation createConversation(
            @RequestHeader("X-User-Id") String senderId,
            @RequestBody CreateConversationRequest request
    ) {

        return conversationService
                .createConversation(senderId, request.getReceiverId());
    }

    @GetMapping
    public List<Conversation> getUserConversations(
            @RequestHeader("X-User-Id") String userId
    ) {

        return conversationService.getUserConversations(userId);
    }

    @GetMapping("/{conversationId}")
    public Conversation getConversation(
            @PathVariable String conversationId
    ) {

        return conversationService.getConversation(conversationId);
    }
}