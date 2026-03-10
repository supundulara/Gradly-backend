package com.Gradly.messaging_service.controller;

import com.Gradly.messaging_service.dto.SendMessageRequest;
import com.Gradly.messaging_service.model.Message;
import com.Gradly.messaging_service.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations/{conversationId}/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public List<Message> getMessages(
            @PathVariable String conversationId
    ) {

        return messageService.getMessages(conversationId);
    }

    @PostMapping
    public Message sendMessage(
            @PathVariable String conversationId,
            @RequestHeader("X-User-Id") String senderId,
            @RequestBody SendMessageRequest request
    ) {

        return messageService
                .sendMessage(conversationId, senderId, request.getContent());
    }
}