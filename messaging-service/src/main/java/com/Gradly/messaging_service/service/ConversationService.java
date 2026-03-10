package com.Gradly.messaging_service.service;

import com.Gradly.messaging_service.client.UserClient;
import com.Gradly.messaging_service.model.Conversation;
import com.Gradly.messaging_service.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserClient userClient;

    public Conversation createConversation(String senderId, String receiverId) {

        List<Conversation> conversations =
                conversationRepository.findByParticipantIdsContaining(senderId);

        for (Conversation conversation : conversations) {

            if (conversation.getParticipantIds().contains(receiverId)) {
                return conversation;
            }
        }

        String senderName = userClient.getUser(senderId).getName();
        String receiverName = userClient.getUser(receiverId).getName();

        Conversation conversation = new Conversation();
        conversation.setParticipantIds(Arrays.asList(senderId, receiverId));
        conversation.setParticipantNames(Arrays.asList(senderName, receiverName));
        conversation.setCreatedAt(LocalDateTime.now());
        conversation.setUpdatedAt(LocalDateTime.now());
        conversation.setLastMessage("");

        return conversationRepository.save(conversation);
    }

    public List<Conversation> getUserConversations(String userId) {

        return conversationRepository.findByParticipantIdsContaining(userId);
    }

    public Conversation getConversation(String conversationId) {

        return conversationRepository
                .findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
    }
}