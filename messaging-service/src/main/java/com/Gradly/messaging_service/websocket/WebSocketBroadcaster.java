package com.Gradly.messaging_service.websocket;

import com.Gradly.messaging_service.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketBroadcaster {

    private final WebSocketSessionManager sessionManager;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public void broadcastMessage(String conversationId, Message message) {
        Set<WebSocketSession> sessions = sessionManager.getSessions(conversationId);
        if (sessions.isEmpty()) return;

        try {
            String payload = objectMapper.writeValueAsString(message);
            TextMessage textMessage = new TextMessage(payload);

            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    try {
                        session.sendMessage(textMessage);
                    } catch (IOException e) {
                        log.error("Failed to send message to session={}", session.getId(), e);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to serialize message for broadcast", e);
        }
    }
}

