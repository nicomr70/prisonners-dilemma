package fr.uga.l3miage.pc.prisonersdilemma.enpoints;

import fr.uga.l3miage.pc.prisonersdilemma.controllers.GameController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;


public class GameEnpoints extends TextWebSocketHandler {

    private final GameController gameController;

    public GameEnpoints( GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();

        if (payload.startsWith("CREATE_GAME")) {
            gameController.createGame(session, payload);
        } else if (payload.startsWith("JOIN_GAME")) {
            gameController.joinGame(session, payload);
        }
        else if (payload.startsWith("ACTION")) {
            gameController.action(session, payload);
        }

    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        gameController.leaveGames(session);
    }

}
