package fr.uga.l3miage.pc.prisonersdilemma;


import fr.uga.l3miage.pc.prisonersdilemma.controllers.GameController;
import fr.uga.l3miage.pc.prisonersdilemma.enpoints.GameEnpoints;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

@SpringBootTest
class GameEnpointsTest {

    private GameEnpoints gameEndpoints;
    private GameController mockGameController;
    private WebSocketSession mockSession;

    @BeforeEach
    void setUp() {
        mockGameController = Mockito.mock(GameController.class);
        mockSession = Mockito.mock(WebSocketSession.class);

        gameEndpoints = new GameEnpoints(mockGameController);
    }

    @Test
    void testHandleTextMessage_CallsCreateGame() throws IOException {
        TextMessage message = new TextMessage("CREATE_GAME");

        gameEndpoints.handleTextMessage(mockSession, message);

        verify(mockGameController, times(1)).createGame(mockSession, "CREATE_GAME");
    }

    @Test
    void testHandleTextMessage_CallsJoinGame() throws IOException {
        TextMessage message = new TextMessage("JOIN_GAME");

        gameEndpoints.handleTextMessage(mockSession, message);

        verify(mockGameController, times(1)).joinGame(mockSession, "JOIN_GAME");
    }

    @Test
    void testHandleTextMessage_IgnoresUnknownAction() throws IOException {
        TextMessage message = new TextMessage("UNKNOWN_ACTION");

        gameEndpoints.handleTextMessage(mockSession, message);

        verify(mockGameController, never()).createGame(any(WebSocketSession.class), anyString());
        verify(mockGameController, never()).joinGame(any(WebSocketSession.class), anyString());
    }

    @Test
    void testAfterConnectionClosed_CallsLeaveGames() {
        CloseStatus status = CloseStatus.NORMAL;

        gameEndpoints.afterConnectionClosed(mockSession, status);

        verify(mockGameController, times(1)).leaveGames(mockSession);
    }

    @Test
    void testHandleTextMessage_CallsAction() throws IOException {
        TextMessage message = new TextMessage("ACTION");

        gameEndpoints.handleTextMessage(mockSession, message);

        verify(mockGameController, times(1)).action(mockSession, "ACTION");
    }
}
