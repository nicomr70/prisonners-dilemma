package fr.uga.l3miage.pc.prisonersdilemma.handlers;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyWebSocketHandler extends TextWebSocketHandler {

    // Map to store rooms and associated sessions
    private final Map<String, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();

        if (payload.startsWith("CREATE_ROOM")) {
            handleRoomCreation(session);
        } else if (payload.startsWith("JOIN_ROOM")) {
            String roomId = extractRoomId(payload);
            handleRoomJoining(session, roomId);
        } else if (payload.startsWith("MESSAGE")) {
            String roomId = extractRoomId(payload);
            String roomMessage = extractMessageContent(payload);
            broadcastMessageToRoom(roomId, roomMessage);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        removeSessionFromAllRooms(session);
    }

    // Room Creation
    private void handleRoomCreation(WebSocketSession session) throws IOException {
        String roomId = generateRoomId();
        addSessionToRoom(session, roomId);
        session.sendMessage(new TextMessage("ROOM_CREATED:" + roomId));
    }

    // Room Joining
    private void handleRoomJoining(WebSocketSession session, String roomId) throws IOException {
        if (rooms.containsKey(roomId)) {
            addSessionToRoom(session, roomId);
            session.sendMessage(new TextMessage("JOINED_ROOM:" + roomId));
        } else {
            session.sendMessage(new TextMessage("ERROR: Room does not exist"));
        }
    }

    // Broadcast a message to all sessions in a specific room
    private void broadcastMessageToRoom(String roomId, String message) throws IOException {
        if (!rooms.containsKey(roomId)) {
            return;
        }

        for (WebSocketSession wsSession : rooms.get(roomId)) {
            if (wsSession.isOpen()) {
                wsSession.sendMessage(new TextMessage("MESSAGE:" + message));
            }
        }
    }

    // Remove a session from all rooms (e.g., on disconnect)
    private void removeSessionFromAllRooms(WebSocketSession session) {
        rooms.values().forEach(sessions -> sessions.remove(session));
    }

    // Helper Methods
    private String generateRoomId() {
        return UUID.randomUUID().toString();
    }

    private String extractRoomId(String payload) {
        return payload.split(":")[1];
    }

    private String extractMessageContent(String payload) {
        return payload.split(":", 3)[2];
    }

    private void addSessionToRoom(WebSocketSession session, String roomId) {
        rooms.putIfAbsent(roomId, ConcurrentHashMap.newKeySet());
        rooms.get(roomId).add(session);
    }
}
