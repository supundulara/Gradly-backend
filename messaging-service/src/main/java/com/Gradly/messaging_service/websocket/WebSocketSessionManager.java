package com.Gradly.messaging_service.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

    // conversationId -> Set of active WebSocket sessions
    private final Map<String, Set<WebSocketSession>> conversationSessions = new ConcurrentHashMap<>();

    public void addSession(String conversationId, WebSocketSession session) {
        conversationSessions
                .computeIfAbsent(conversationId, k -> Collections.newSetFromMap(new ConcurrentHashMap<>()))
                .add(session);
    }

    public void removeSession(String conversationId, WebSocketSession session) {
        Set<WebSocketSession> sessions = conversationSessions.get(conversationId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                conversationSessions.remove(conversationId);
            }
        }
    }

    public Set<WebSocketSession> getSessions(String conversationId) {
        return conversationSessions.getOrDefault(conversationId, Collections.emptySet());
    }
}

