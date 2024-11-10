package fr.uga.l3miage.pc.prisonersdilemma.handlers;

import fr.uga.l3miage.pc.prisonersdilemma.controllers.GameController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyWebSocketHandler extends TextWebSocketHandler {

    // Map to store rooms and associated sessions
    private final Map<String, Set<WebSocketSession>> currentGames = new ConcurrentHashMap<>();
    private final GameController gameController = GameController.getInstance();


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();

        if (payload.startsWith("CREATE_GAME")) {
            handleGameCreation(session, payload);
        } else if (payload.startsWith("JOIN_GAME")) {
            String roomId = extractGameId(payload);
            joinGame(session, roomId);
        }
        else if (payload.startsWith("ACTION")) {
//            String roomId = extractGameId(payload);
//            String roomMessage = extractMessageContent(payload);
//            broadcastActionToGame(roomId, roomMessage);
        }

    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        removeSessionFromAllRooms(session);
    }

    // Room Creation
    public void handleGameCreation(WebSocketSession session, String payload) throws IOException {

    }



    private void joinGame(WebSocketSession session, String gameId) throws IOException {
        if (currentGames.containsKey(gameId)) {
            addPlayerToGame(session, gameId);
            session.sendMessage(new TextMessage("JOINED_ROOM:" + gameId));
        } else {
            session.sendMessage(new TextMessage("ERROR: Room does not exist"));
        }
    }

    // Broadcast a message to all sessions in a specific room
//    private void broadcastActionToGame(String gameId, String message) throws IOException {
//        if (!currentGames.containsKey(gameId)) {
//            return;
//        }
//
//        for (WebSocketSession wsSession : currentGames.get(gameId)) {
//            if (wsSession.isOpen()) {
//                wsSession.sendMessage(new TextMessage("MESSAGE:" + message));
//            }
//        }
//    }

    // Remove a session from all rooms (e.g., on disconnect)
    private void removeSessionFromAllRooms(WebSocketSession session) {
        currentGames.values().forEach(sessions -> sessions.remove(session));
    }


    private String extractGameId(String payload) {
        return payload.split(":")[1];
    }

    private String extractMessageContent(String payload) {
        return payload.split(":", 3)[2];
    }

    private void addPlayerToGame(WebSocketSession session, String gameId) {
        currentGames.putIfAbsent(gameId, ConcurrentHashMap.newKeySet());
        currentGames.get(gameId).add(session);
    }
}
