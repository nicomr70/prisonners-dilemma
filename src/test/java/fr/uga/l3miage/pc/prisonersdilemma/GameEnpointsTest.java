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
public class GameEnpointsTest {

    private GameEnpoints gameEndpoints;
    private GameController mockGameController;
    private WebSocketSession mockSession;

    @BeforeEach
    void setUp() {
        // Create mock GameController and WebSocketSession
        mockGameController = Mockito.mock(GameController.class);
        mockSession = Mockito.mock(WebSocketSession.class);

        // Inject mock GameController into GameEnpoints (you'll need to modify GameEnpoints to allow this for testing)
        gameEndpoints = new GameEnpoints(mockGameController);
    }

    @Test
    void testHandleTextMessage_CallsCreateGame() throws IOException {
        // Prepare test message for "CREATE_GAME"
        TextMessage message = new TextMessage("CREATE_GAME");

        // Call the method
        gameEndpoints.handleTextMessage(mockSession, message);

        // Verify that createGame is called on the controller
        verify(mockGameController, times(1)).createGame(mockSession, "CREATE_GAME");
    }

    @Test
    void testHandleTextMessage_CallsJoinGame() throws IOException {
        // Prepare test message for "JOIN_GAME"
        TextMessage message = new TextMessage("JOIN_GAME");

        // Call the method
        gameEndpoints.handleTextMessage(mockSession, message);

        // Verify that joinGame is called on the controller
        verify(mockGameController, times(1)).joinGame(mockSession, "JOIN_GAME");
    }

    @Test
    void testHandleTextMessage_IgnoresUnknownAction() throws IOException {
        // Prepare an unknown action message
        TextMessage message = new TextMessage("UNKNOWN_ACTION");

        // Call the method
        gameEndpoints.handleTextMessage(mockSession, message);

        // Verify that neither createGame nor joinGame are called
        verify(mockGameController, never()).createGame(any(WebSocketSession.class), anyString());
        verify(mockGameController, never()).joinGame(any(WebSocketSession.class), anyString());
    }

    @Test
    void testAfterConnectionClosed_CallsLeaveGames() {
        // Prepare close status
        CloseStatus status = CloseStatus.NORMAL;

        // Call the method
        gameEndpoints.afterConnectionClosed(mockSession, status);

        // Verify that leaveGames is called on the controller
        verify(mockGameController, times(1)).leaveGames(mockSession);
    }

    @Test
    void testHandleTextMessage_CallsAction() throws IOException {
        // Prepare test message for "ACTION"
        TextMessage message = new TextMessage("ACTION");

        // Call the method
        gameEndpoints.handleTextMessage(mockSession, message);

        // Verify that joinGame is called on the controller
        verify(mockGameController, times(1)).action(mockSession, "ACTION");
    }
}
