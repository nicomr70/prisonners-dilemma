package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.prisonersdilemma.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameServiceTest {

	private GameService gameService;
	private WebSocketSession mockSession;

	@BeforeEach
	void setUp() {
		gameService = GameService.getInstance();
		mockSession = Mockito.mock(WebSocketSession.class);
	}

	@Test
	void createGame_ShouldReturnNonNullGameId() {
		// Act
		String gameId = gameService.createGame(mockSession);

		// Assert
		assertNotNull(gameId, "Game ID should not be null after game creation");
	}

	@Test
	void createGame_ShouldAddSessionToNewGame() {
		// Act
		String gameId = gameService.createGame(mockSession);
		Set<WebSocketSession> playersInGame = gameService.getPlayers(gameId);

		// Assert
		assertNotNull(playersInGame, "Players set should be initialized for the new game");
		assertEquals(1, playersInGame.size(), "There should be exactly one player in the new game");
		assertTrue(playersInGame.contains(mockSession), "The player's session should be added to the game");
	}

	@Test
	void createGame_ShouldCreateUniqueGameIds() {
		// Arrange
		WebSocketSession mockSession2 = Mockito.mock(WebSocketSession.class);

		// Act
		String gameId1 = gameService.createGame(mockSession);
		String gameId2 = gameService.createGame(mockSession2);

		// Assert
		assertNotNull(gameId1, "Game ID 1 should not be null");
		assertNotNull(gameId2, "Game ID 2 should not be null");
		assertNotEquals(gameId1, gameId2, "Each game should have a unique ID");
	}
}
