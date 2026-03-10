package com.Gradly.messaging_service.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionManager sessionManager;

    // session id -> conversationId (for cleanup on disconnect)
    private final Map<String, String> sessionConversationMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        String conversationId = extractConversationId(session);
        if (conversationId != null) {
            sessionManager.addSession(conversationId, session);
            sessionConversationMap.put(session.getId(), conversationId);
            log.info("WebSocket connected: session={}, conversationId={}", session.getId(), conversationId);
        }
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        String conversationId = sessionConversationMap.remove(session.getId());
        if (conversationId != null) {
            sessionManager.removeSession(conversationId, session);
            log.info("WebSocket disconnected: session={}, conversationId={}", session.getId(), conversationId);
        }
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) {
        log.error("WebSocket transport error: session={}", session.getId(), exception);
    }

    /**
     * Extracts the conversationId from the WebSocket URI.
     * e.g., /ws/conversations/{conversationId}
     */
    private String extractConversationId(WebSocketSession session) {
        String path = session.getUri() != null ? session.getUri().getPath() : null;
        if (path == null) return null;

        // path = /ws/conversations/{conversationId}
        String[] parts = path.split("/");
        // parts: ["", "ws", "conversations", "{conversationId}"]
        if (parts.length >= 4) {
            return parts[3];
        }
        return null;
    }
}

