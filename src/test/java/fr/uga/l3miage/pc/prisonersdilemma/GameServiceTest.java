package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;
import fr.uga.l3miage.pc.prisonersdilemma.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class GameServiceTest {

	private GameService gameService;
	private WebSocketSession mockSession;

	@BeforeEach
	void setUp() {
		gameService = GameService.getInstance();
		mockSession = mock(WebSocketSession.class);
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
		WebSocketSession mockSession2 = mock(WebSocketSession.class);

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
		WebSocketSession mockSession2 = mock(WebSocketSession.class);
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
		WebSocketSession mockSession2 = mock(WebSocketSession.class);
		WebSocketSession mockSession3 = mock(WebSocketSession.class);
		// join the game with the session
		gameService.joinGame(mockSession2, "JOIN_GAME:" + gameId);
		// assert that the session is in the game
		assertThrows(IllegalArgumentException.class, () -> gameService.joinGame(mockSession3, "JOIN_GAME:" + gameId));
	}

	@Test
	void testJoiningNonExistentGame() throws IOException {

		// mock a session
		WebSocketSession mockSession2 = mock(WebSocketSession.class);

		assertThrows(IllegalArgumentException.class, () -> gameService.joinGame(mockSession2, "JOIN_GAME:12345"));
	}

	@Test
	void testPlayerOneReceivesCorrectMessageWhenPlayerTwoJoins() throws IOException {
		// Arrange
		WebSocketSession mockSession1 = mock(WebSocketSession.class); // Player One
		WebSocketSession mockSession2 = mock(WebSocketSession.class); // Player Two

		String gameId = gameService.createGame(mockSession1, "CREATE_GAME:10");
		gameService.joinGame(mockSession2, "JOIN_GAME:" + gameId);

		// Capture all messages sent to Player One
		ArgumentCaptor<TextMessage> messageCaptor = ArgumentCaptor.forClass(TextMessage.class);

		// Verify sendMessage was called for Player One
		verify(mockSession1, times(2)).sendMessage(messageCaptor.capture());

		// Retrieve all messages sent to Player One
		List<TextMessage> capturedMessages = messageCaptor.getAllValues();

		// Assert the second message received by Player One
		String expectedMessage = "PLAYER_TWO_JOINED";
		assertEquals(expectedMessage, capturedMessages.get(1).getPayload());
	}

	@Test
	void testGetPlayersShouldThrowExceptionIfGameNotFound() {
		// Act
		assertThrows(IllegalArgumentException.class, () -> gameService.getPlayers("12345"));
	}

	@Test
	void testGetPlayersShouldReturnPlayers() throws IOException {
		// Arrange
		String gameId = gameService.createGame(mockSession, "CREATE_GAME:10");
		WebSocketSession mockSession2 = mock(WebSocketSession.class);
		gameService.joinGame(mockSession2, "JOIN_GAME:" + gameId);

		// Act
		Set<WebSocketSession> players = gameService.getPlayers(gameId);

		// Assert
		assertNotNull(players, "Players set should not be null");
		assertEquals(2, players.size(), "Players set should contain one player");
		assertTrue(players.contains(mockSession), "Players set should contain the player");
		assertTrue(players.contains(mockSession2), "Players set should contain the player");
	}

	// the protocol for actions is "ACTION:12345:COOPERATE"


		@Test
		void testExtractPlayerActionFromPayloadWorking() {
			// Arrange
			String payload = "ACTION:12345:COOPERATE";
			String payload2 = "ACTION:12345:BETRAY";

			// Act
			String action = gameService.extractPlayerActionFromPayload(payload);
			String action2 = gameService.extractPlayerActionFromPayload(payload2);

			// Assert
			assertEquals("COOPERATE", action);
			assertEquals("BETRAY", action2);
		}

		@Test
		void testExtractPlayerActionFromPayloadNotWorking() {
			// Arrange
			String payload = "ACTION:12345:COOPERATE";
			String payload2 = "ACTION:12345:BETRAY";

			// Act
			String action = gameService.extractPlayerActionFromPayload(payload);
			String action2 = gameService.extractPlayerActionFromPayload(payload2);

			// Assert
			assertNotEquals("BETRAY", action);
			assertNotEquals("COOPERATE", action2);
		}

		@Test
		void testGetPlayerNumber(){
			//Arrange
			WebSocketSession mockPlayerOne = mock(WebSocketSession.class);
			Game game = new Game(10, mockPlayerOne);

			// Act
			PlayerNumber result = gameService.getPlayerNumber(mockPlayerOne, game);

			// Assert
			assertEquals(PlayerNumber.PLAYER_ONE, result, "Should return PLAYER_ONE for playerOne session");
		}

		@Test
		void testGetPlayerNumberPlayerTwo(){
			//Arrange
			WebSocketSession mockPlayerOne = mock(WebSocketSession.class);
			WebSocketSession mockPlayerTwo = mock(WebSocketSession.class);
			Game game = new Game(10, mockPlayerOne);
			game.addSecondPlayerToTheGame(mockPlayerTwo);

			// Act
			PlayerNumber result = gameService.getPlayerNumber(mockPlayerTwo, game);

			// Assert
			assertEquals(PlayerNumber.PLAYER_TWO, result, "Should return PLAYER_TWO for playerTwo session");
		}

		@Test
		void testGetPlayerNumberPlayerNotFound(){
			//Arrange
			WebSocketSession mockPlayerOne = mock(WebSocketSession.class);
			WebSocketSession mockPlayerTwo = mock(WebSocketSession.class);
			WebSocketSession mockPlayerThree = mock(WebSocketSession.class);
			Game game = new Game(10, mockPlayerOne);
			game.addSecondPlayerToTheGame(mockPlayerTwo);

			// Act
			assertThrows(IllegalArgumentException.class, () -> gameService.getPlayerNumber(mockPlayerThree, game));
		}

		@Test
		void actionShouldThrowExceptionIfGameNotFound() {
			// Act
			assertThrows(IllegalArgumentException.class, () -> gameService.action(mockSession, "ACTION:12345:COOPERATE"));
		}

		@Test
		void actionShouldThrowExceptionIfPlayerNotInGame() throws IOException {
			// Arrange
			String gameId = gameService.createGame(mockSession, "CREATE_GAME:10");
			WebSocketSession mockSession2 = mock(WebSocketSession.class);
			gameService.joinGame(mockSession2, "JOIN_GAME:" + gameId);
			WebSocketSession mockSession3 = mock(WebSocketSession.class);

			// Act
			assertThrows(IllegalArgumentException.class, () -> gameService.action(mockSession3, "ACTION:" + gameId + ":COOPERATE"));
		}

		@Test
		void actionShouldThrowExceptionIfPlayerActionNotValid() throws IOException {
			// Arrange
			String gameId = gameService.createGame(mockSession, "CREATE_GAME:10");

			// Act
			assertThrows(IllegalArgumentException.class, () -> gameService.action(mockSession, "ACTION:" + gameId + ":INVALID_ACTION"));
		}

		@Test
		void testActionGameNotExist(){
			assertThrows(IllegalArgumentException.class, () -> gameService.getPlayerAction("12345", mockSession,0));
		}

		@Test
		void testActionPlayerNotExist() throws IOException {
			String gameId = gameService.createGame(mockSession, "CREATE_GAME:10");
			assertThrows(IllegalArgumentException.class, () -> gameService.getPlayerAction(gameId, mock(WebSocketSession.class),0));
		}

		@Test
		void testPlayerOneActionCooperate() throws IOException {

			WebSocketSession mockSession1 = mock(WebSocketSession.class);
			WebSocketSession mockSession2 = mock(WebSocketSession.class);
			String gameId = gameService.createGame(mockSession1, "CREATE_GAME:50000");
			gameService.joinGame(mockSession2, "JOIN_GAME:" + gameId);
			// Act
			gameService.action(mockSession1, "ACTION:" + gameId + ":COOPERATE");

			// Assert
			Action playerOneAction = gameService.getPlayerAction(gameId, mockSession1,0);
			assertEquals(Action.COOPERATE, playerOneAction, "Player one's action should be COOPERATE.");
		}

		@Test
		void testPlayerTwoActionBetray() throws IOException {
			WebSocketSession mockSession1 = mock(WebSocketSession.class);
			WebSocketSession mockSession2 = mock(WebSocketSession.class);
			String gameId2 = gameService.createGame(mockSession1, "CREATE_GAME:50000");
			gameService.joinGame(mockSession2, "JOIN_GAME:" + gameId2);
			gameService.action(mockSession1, "ACTION:" + gameId2 + ":COOPERATE");
			// Act
			gameService.action(mockSession2, "ACTION:" + gameId2 + ":BETRAY");

			// Assert
			Action playerTwoAction = gameService.getPlayerAction(gameId2, mockSession2,0);
			assertEquals(Action.BETRAY, playerTwoAction, "Player two's action should be BETRAY.");
		}

		@Test
		void testBothPlayersPerformActions() throws IOException {

			// Arrange
			WebSocketSession mockSession = mock(WebSocketSession.class);
			WebSocketSession mockSession2 = mock(WebSocketSession.class);

			// Create a game and add the first player
			String gameId2 = gameService.createGame(mockSession, "CREATE_GAME:50000");
			gameService.joinGame(mockSession2, "JOIN_GAME:" + gameId2);

			// Act
			gameService.action(mockSession,"ACTION:" + gameId2 + ":COOPERATE");
			gameService.action(mockSession2, "ACTION:" + gameId2 + ":BETRAY");

			// Assert
			Action playerOneAction = gameService.getPlayerAction(gameId2, mockSession,0);
			Action playerTwoAction = gameService.getPlayerAction(gameId2, mockSession2,0);

			assertEquals(Action.COOPERATE, playerOneAction, "Player one's action should be COOPERATE.");
			assertEquals(Action.BETRAY, playerTwoAction, "Player two's action should be BETRAY.");
		}

		@Test
		void testActionCallsSendTurnSummaryToBothPlayers() throws IOException {
			// Arrange
			WebSocketSession mockSession1 = mock(WebSocketSession.class);
			WebSocketSession mockSession2 = mock(WebSocketSession.class);
			GameService gameService1 = Mockito.spy(gameService);
			String gameId = gameService1.createGame(mockSession1, "CREATE_GAME:50000");
			gameService1.joinGame(mockSession2, "JOIN_GAME:" + gameId);
			gameService1.action(mockSession1, "ACTION:" + gameId + ":COOPERATE");
			gameService1.action(mockSession2, "ACTION:" + gameId + ":BETRAY");

			// Verify sendTurnSummaryToBothPlayers was called once
			verify(gameService1, times(1)).sendTurnSummaryToBothPlayers(gameId,0);
		}

		@Test
		void sendTurnSummaryToBothPlayers() throws IOException {
			// Arrange
			WebSocketSession mockSession1 = mock(WebSocketSession.class);
			WebSocketSession mockSession2 = mock(WebSocketSession.class);
			String gameId = gameService.createGame(mockSession1, "CREATE_GAME:50000");
			gameService.joinGame(mockSession2, "JOIN_GAME:" + gameId);
			gameService.action(mockSession1, "ACTION:" + gameId + ":COOPERATE");
			gameService.action(mockSession2, "ACTION:" + gameId + ":BETRAY");
			String expectedMessage = "TURN_SUMMARY:COOPERATE:BETRAY";

			// Capture the argument passed to sendMessage
			ArgumentCaptor<TextMessage> messageCaptor = ArgumentCaptor.forClass(TextMessage.class);

			// Verify sendMessage was called once and capture the argument
			verify(mockSession1, times(3)).sendMessage(messageCaptor.capture());
			verify(mockSession2, times(1)).sendMessage(messageCaptor.capture());

			// Assert the captured message
			TextMessage sentMessage = messageCaptor.getValue();
			assertEquals(expectedMessage, sentMessage.getPayload());
		}


//	@Test
//	void testActionCooperatePlayerOne() throws IOException {
//		// Arrange
//		WebSocketSession mockSession2 = Mockito.mock(WebSocketSession.class);
//		String gameId = gameService.createGame(mockSession, "CREATE_GAME:10");
//		gameService.joinGame(mockSession2, "JOIN_GAME:" + gameId);
//
//		// Act
//		gameService.action(mockSession, "ACTION:COOPERATE"); // player one Cooperate
//
//		// Assert
//		assertEquals(Action.COOPERATE, gameService.getPlayerAction(gameId, mockSession));
//	}

//	@Test
//	void testActionBetrayPlayerOne() throws IOException {
//		// Arrange
//		WebSocketSession mockSession2 = Mockito.mock(WebSocketSession.class);
//		String gameId = gameService.createGame(mockSession, "CREATE_GAME:10");
//		gameService.joinGame(mockSession2, "JOIN_GAME:" + gameId);
//
//		// Act
//		gameService.action(mockSession, "ACTION:BETRAY"); // player one Betray
//
//		// Assert
//		assertEquals(Action.BETRAY, gameService.getPlayerAction(gameId, mockSession));
//	}
//
//	@Test
//	void testActionCooperatePlayerTwo() throws IOException {
//		// Arrange
//		WebSocketSession mockSession2 = Mockito.mock(WebSocketSession.class);
//		String gameId = gameService.createGame(mockSession, "CREATE_GAME:10");
//		gameService.joinGame(mockSession2, "JOIN_GAME:" + gameId);
//
//		// Act
//		gameService.action(mockSession2, "ACTION:COOPERATE"); // player two Cooperate
//
//		// Assert
//		assertEquals(Action.COOPERATE, gameService.getPlayerAction(gameId, mockSession2));
//	}
//
//	@Test
//	void testActionBetrayPlayerTwo() throws IOException {
//		// Arrange
//		WebSocketSession mockSession2 = Mockito.mock(WebSocketSession.class);
//		String gameId = gameService.createGame(mockSession, "CREATE_GAME:10");
//		gameService.joinGame(mockSession2, "JOIN_GAME:" + gameId);
//
//		// Act
//		gameService.action(mockSession2, "ACTION:BETRAY"); // player two Betray
//
//		// Assert
//		assertEquals(Action.BETRAY, gameService.getPlayerAction(gameId, mockSession2));
//	}



}
