package com.Gradly.messaging_service.service;

import com.Gradly.messaging_service.client.UserClient;
import com.Gradly.messaging_service.model.Conversation;
import com.Gradly.messaging_service.model.Message;
import com.Gradly.messaging_service.repository.ConversationRepository;
import com.Gradly.messaging_service.repository.MessageRepository;
import com.Gradly.messaging_service.websocket.WebSocketBroadcaster;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserClient userClient;
    private final WebSocketBroadcaster webSocketBroadcaster;

    public List<Message> getMessages(String conversationId) {

        return messageRepository
                .findByConversationIdOrderByCreatedAtAsc(conversationId);
    }

    public Message sendMessage(String conversationId,
                               String senderId,
                               String content) {

        Conversation conversation =
                conversationRepository
                        .findById(conversationId)
                        .orElseThrow(() -> new RuntimeException("Conversation not found"));

        if (!conversation.getParticipantIds().contains(senderId)) {
            throw new RuntimeException("User not allowed in this conversation");
        }

        String senderName = userClient.getUser(senderId).getName();

        Message message = new Message();
        message.setConversationId(conversationId);
        message.setSenderId(senderId);
        message.setSenderName(senderName);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);

        conversation.setLastMessage(content);
        conversation.setUpdatedAt(LocalDateTime.now());
        conversationRepository.save(conversation);

        // Broadcast the new message to all connected WebSocket clients in this conversation
        webSocketBroadcaster.broadcastMessage(conversationId, savedMessage);

        return savedMessage;
    }
}