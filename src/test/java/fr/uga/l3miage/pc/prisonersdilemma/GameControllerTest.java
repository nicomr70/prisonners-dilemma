package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.prisonersdilemma.controllers.GameController;
import fr.uga.l3miage.pc.prisonersdilemma.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class GameControllerTest {
    private GameController gameController;
    private GameService mockGameService;
    private WebSocketSession mockSession;

    @BeforeEach
    void setUp() throws Exception {
        // Reset singleton instance using reflection
        Field instanceField = GameController.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);

        // Create mock GameService and WebSocketSession
        mockGameService = Mockito.mock(GameService.class);
        mockSession = Mockito.mock(WebSocketSession.class);

        // Set GameController to use the mocked GameService
        gameController = GameController.getInstance();
        Field gameServiceField = GameController.class.getDeclaredField("gameService");
        gameServiceField.setAccessible(true);
        gameServiceField.set(gameController, mockGameService);
    }

    @Test
    void testCreateGame_CallsServiceCreateGame() throws IOException {
        String payload = "CREATE_GAME";

        // Call the method
        gameController.createGame(mockSession, payload);

        // Verify that the service's createGame method is called
        verify(mockGameService, times(1)).createGame(mockSession, payload);
    }

    @Test
    void testJoinGame_CallsServiceJoinGame() throws IOException {
        String payload = "JOIN_GAME";

        // Call the method
        gameController.joinGame(mockSession, payload);

        // Verify that the service's joinGame method is called
        verify(mockGameService, times(1)).joinGame(mockSession, payload);
    }

    @Test
    void testLeaveGames_CallsServiceLeaveGames() {
        // Call the method
        gameController.leaveGames(mockSession);

        // Verify that the service's leaveGames method is called
        verify(mockGameService, times(1)).leaveGames(mockSession);
    }

    @Test
    void testAction_CallsServiceAction() {
        String payload = "ACTION";

        // Call the method
        gameController.action(mockSession, payload);

        // Verify that the service's action method is called
        verify(mockGameService, times(1)).action(mockSession, payload);
    }
}
