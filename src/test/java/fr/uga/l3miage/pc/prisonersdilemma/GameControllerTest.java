package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.prisonersdilemma.controllers.GameController;
import fr.uga.l3miage.pc.prisonersdilemma.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class GameControllerTest {
    private GameController gameController;
    private GameService mockGameService;
    private WebSocketSession mockSession;

    @BeforeEach
    void setUp() throws Exception {
        Field instanceField = GameController.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);

        mockGameService = Mockito.mock(GameService.class);
        mockSession = Mockito.mock(WebSocketSession.class);

        gameController = GameController.getInstance();
        Field gameServiceField = GameController.class.getDeclaredField("gameService");
        gameServiceField.setAccessible(true);
        gameServiceField.set(gameController, mockGameService);
    }

    @Test
    void testCreateGame_CallsServiceCreateGame() throws IOException {
        String payload = "CREATE_GAME";

        gameController.createGame(mockSession, payload);

        verify(mockGameService, times(1)).createGame(mockSession, payload);
    }

    @Test
    void testJoinGame_CallsServiceJoinGame() throws IOException {
        String payload = "JOIN_GAME";

        gameController.joinGame(mockSession, payload);

        verify(mockGameService, times(1)).joinGame(mockSession, payload);
    }

    @Test
    void testLeaveGames_CallsServiceLeaveGames() {
        gameController.leaveGames(mockSession);

        verify(mockGameService, times(1)).leaveGames(mockSession);
    }

    @Test
    void testAction_CallsServiceAction() {
        String payload = "ACTION";

        gameController.action(mockSession, payload);

        verify(mockGameService, times(1)).action(mockSession, payload);
    }
}
