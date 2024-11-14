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
            gameController.createGame(session, payload);
        } else if (payload.startsWith("JOIN_GAME")) {
            gameController.joinGame(session, payload);
        }
        else if (payload.startsWith("ACTION")) {

        }

    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        gameController.leaveGames(session);
    }

}
