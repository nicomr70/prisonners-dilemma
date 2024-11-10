package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.prisonersdilemma.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
	void createGame_ShouldThrowAnException_notANumber() throws IOException {
		assertThrows(IOException.class, () -> gameService.createGame(mockSession,"CREATE_GAME:test"));
	}

	@Test
	void createGame_payload_should_start_with_CREATE_GAME() throws IOException {
		assertThrows(IOException.class, () -> gameService.createGame(mockSession,"test"));
	}

	@Test
	void createGame_ShouldAddSessionToNewGame() throws IOException {
		// Act
		String gameId = gameService.createGame(mockSession, "CREATE_GAME:10");
		Set<WebSocketSession> playersInGame = gameService.getPlayers(gameId);

		// Assert
		assertNotNull(playersInGame, "Players set should be initialized for the new game");
		assertEquals(1, playersInGame.size(), "There should be exactly one player in the new game");
		assertTrue(playersInGame.contains(mockSession), "The player's session should be added to the game");
	}

	@Test
	void createGame_ShouldCreateUniqueGameIds() throws IOException {
		// Arrange
		WebSocketSession mockSession2 = Mockito.mock(WebSocketSession.class);

		// Act
		String gameId1 = gameService.createGame(mockSession, "CREATE_GAME:10");
		String gameId2 = gameService.createGame(mockSession2, "CREATE_GAME:10");

		// Assert
		assertNotNull(gameId1, "Game ID 1 should not be null");
		assertNotNull(gameId2, "Game ID 2 should not be null");
		assertNotEquals(gameId1, gameId2, "Each game should have a unique ID");
	}

	@Test
	void testSendGameIdToPlayer() throws IOException {
		// Arrange
		String gameId = "12345";
		String expectedMessage = "GAME_ID:" + gameId;

		// Act
		gameService.sendGameIdToPlayer(mockSession, gameId);

		// Capture the argument passed to sendMessage
		ArgumentCaptor<TextMessage> messageCaptor = ArgumentCaptor.forClass(TextMessage.class);

		// Verify sendMessage was called once and capture the argument
		verify(mockSession, times(1)).sendMessage(messageCaptor.capture());

		// Assert the captured message
		TextMessage sentMessage = messageCaptor.getValue();
		assertEquals(expectedMessage, sentMessage.getPayload());
	}

	@Test
	void testSuccessfullyJoiningGame() throws IOException {
		// Create a new game
		String gameId = gameService.createGame(mockSession, "CREATE_GAME:10");
		// mock a session
		WebSocketSession mockSession2 = Mockito.mock(WebSocketSession.class);
		// join the game with the session
		gameService.joinGame(mockSession2, "JOIN_GAME:" + gameId);
		// assert that the session is in the game
		assertTrue(gameService.getPlayers(gameId).contains(mockSession2));
	}

	@Test
	void testJoiningFullGame() throws IOException {
		// Create a new game
		String gameId = gameService.createGame(mockSession, "CREATE_GAME:10");
		// mock a session
		WebSocketSession mockSession2 = Mockito.mock(WebSocketSession.class);
		WebSocketSession mockSession3 = Mockito.mock(WebSocketSession.class);
		// join the game with the session
		gameService.joinGame(mockSession2, "JOIN_GAME:" + gameId);
		// assert that the session is in the game
		assertThrows(IllegalArgumentException.class, () -> gameService.joinGame(mockSession3, "JOIN_GAME:" + gameId));
	}

	@Test
	void testJoiningNonExistentGame() throws IOException {

		// mock a session
		WebSocketSession mockSession2 = Mockito.mock(WebSocketSession.class);

		assertThrows(IllegalArgumentException.class, () -> gameService.joinGame(mockSession2, "JOIN_GAME:12345"));
	}

	@Test
	void testGetPlayersShouldReturnEmptySet() {
		// Act
		Set<WebSocketSession> players = gameService.getPlayers("12345");

		// Assert
		assertNotNull(players, "Players set should not be null");
		assertTrue(players.isEmpty(), "Players set should be empty");
	}

	@Test
	void testGetPlayersShouldReturnPlayers() throws IOException {
		// Arrange
		String gameId = gameService.createGame(mockSession, "CREATE_GAME:10");
		WebSocketSession mockSession2 = Mockito.mock(WebSocketSession.class);
		gameService.joinGame(mockSession2, "JOIN_GAME:" + gameId);

		// Act
		Set<WebSocketSession> players = gameService.getPlayers(gameId);


		// Assert
		assertNotNull(players, "Players set should not be null");
		assertEquals(2, players.size(), "Players set should contain one player");
		assertTrue(players.contains(mockSession), "Players set should contain the player");
		assertTrue(players.contains(mockSession2), "Players set should contain the player");
	}
}
