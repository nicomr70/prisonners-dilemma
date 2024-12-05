package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;
import fr.uga.l3miage.pc.prisonersdilemma.game.states.CompletedState;
import fr.uga.l3miage.pc.prisonersdilemma.game.states.InProgressState;
import fr.uga.l3miage.pc.prisonersdilemma.game.states.State;
import fr.uga.l3miage.pc.prisonersdilemma.game.states.WaitingState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class GameTest {
    private Game game;
    private State mockState;
    private WebSocketSession mockSession1;

    @BeforeEach
    void setUp() {
        mockSession1 = mock(WebSocketSession.class);
        game = new Game(5, mockSession1);
        mockState = mock(State.class);
    }

    @Nested
    class StateTests{
        @BeforeEach
        void setUp() {
            mockSession1 = mock(WebSocketSession.class);
            game = new Game(5, mockSession1);
            mockState = mock(State.class);
        }

        @Test
        void testInitialStateIsWaiting() {
            assertTrue(game.getState() instanceof WaitingState, "Initial state should be WaitingState.");
        }

        @Test
        void testChangeState() {
            game.changeState(mockState);
            assertEquals(mockState, game.getState(), "The game state should be changed to the mock state.");
        }

        @Test
        void testPlayDelegatesToState() {
            WebSocketSession mockSession = mock(WebSocketSession.class);
            game.changeState(mockState);
            game.addSecondPlayerToTheGame(mockSession);
            game.play(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
            verify(mockState, times(1)).play(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
        }

        @Test
        void testChangeStateToInProgressState() {
            WebSocketSession mockSession2 = mock(WebSocketSession.class);
            game.addSecondPlayerToTheGame(mockSession2);
            assertTrue(game.getState() instanceof InProgressState, "The game state should be changed to InProgressState.");
        }

        @Test
        void testChangeStateToCompleted(){
            WebSocketSession mockSession2 = mock(WebSocketSession.class);
            game.addSecondPlayerToTheGame(mockSession2);
            for (int i = 0; i < 5; i++) {
                game.play(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
                game.play(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
            }

            assertTrue(game.getState() instanceof CompletedState, "The game state should be changed to Completed");
        }

    }

    @Test
    void testGenerateGameId() {
        assertNotNull(game.getId(), "Game ID should not be null after initialization.");
    }

    @Nested
    class PlayTurnTests{

        @Test
        void testPlayTurnUpdatesTurnAction() {
            game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_ONE);

            Turn currentTurn = game.getTurns()[0];

            assertEquals(Action.COOPERATE, currentTurn.getPlayerOneAction(), "Player one's action should be set to COOPERATE.");
            game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);

            currentTurn = game.getTurns()[0];
            assertEquals(Action.BETRAY, currentTurn.getPlayerTwoAction(), "Player two's action should be set to BETRAY.");
        }

        @Test
        void testPlayTurnIncrementsCurrentTurn() {
            assertEquals(0, game.getCurrentTurn(), "Current turn should be 0 initially.");

            game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
            assertEquals(0, game.getCurrentTurn(), "Current turn should not be incremented if the player 2 hasn't played yet.");

            game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
            assertEquals(1, game.getCurrentTurn(), "Current turn should not increment after Player Two's action.");

            game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
            assertEquals(1, game.getCurrentTurn(), "Current turn should increment by 1 after Player One's action.");
        }

    }


    @Nested
    class playersTest {

        @BeforeEach
        void setUp() {
            mockSession1 = mock(WebSocketSession.class);
            game = new Game(5, mockSession1);
            mockState = mock(State.class);
            game.changeState(mockState);
        }

        @Test
        void testAddFirstPlayer() {

            assertEquals(mockSession1, game.getPlayerOne(), "Player One should be the first added session");
            assertNull(game.getPlayerTwo(), "Player Two should be null initially");
            assertFalse(game.isFull(), "Game should not be full with only one player");
        }

        @Test
        void testAddSecondPlayer() {
            WebSocketSession mockSession2 = mock(WebSocketSession.class);

            game.addSecondPlayerToTheGame(mockSession2);

            assertEquals(mockSession1, game.getPlayerOne(), "Player One should remain as the initial session");
            assertEquals(mockSession2, game.getPlayerTwo(), "Player Two should be the second added session");
            assertTrue(game.isFull(), "Game should be full after adding two players");
        }


        @Test
        void testAddSecondPlayerWhenGameIsFull() {
            WebSocketSession mockSession2 = mock(WebSocketSession.class);
            game.addSecondPlayerToTheGame(mockSession2);

            WebSocketSession mockSession3 = Mockito.mock(WebSocketSession.class);
            assertThrows(IllegalArgumentException.class, () ->game.addSecondPlayerToTheGame(mockSession3)); // Attempt to add a third player

            assertEquals(mockSession2, game.getPlayerTwo(), "Player Two should still be the second session");
        }

        @Test
        void testRemovePlayerOne() {

            game.removePLayer(mockSession1);

            assertNull(game.getPlayerOne(), "Player One should be null after removal");
            assertNull(game.getPlayerTwo(), "Player Two should be null initially (since not added yet)");
            assertFalse(game.isFull(), "Game should not be full after removing the only player");
        }

        @Test
        void testRemovePlayerTwo() {

            WebSocketSession mockSession2 = mock(WebSocketSession.class);
            game.addSecondPlayerToTheGame(mockSession2);

            game.removePLayer(mockSession2);

            assertEquals(mockSession1, game.getPlayerOne(), "Player One should remain the first session");
            assertNull(game.getPlayerTwo(), "Player Two should be null after removal");
            assertFalse(game.isFull(), "Game should not be full after removing one of two players");
        }

        @Test
        void testIsFull() {
            WebSocketSession mockSession2 = mock(WebSocketSession.class);
            assertFalse(game.isFull(), "Game should not be full with only one player");

            game.addSecondPlayerToTheGame(mockSession2);

            assertTrue(game.isFull(), "Game should be full after adding two players");
        }
    }



    @Test
    void testGetHistoryByPlayerNumberReturnsCorrectActions() {
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_ONE);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);

        List<Action> playerOneHistory = game.getHistoryByPlayerNumber(PlayerNumber.PLAYER_ONE);
        List<Action> playerTwoHistory = game.getHistoryByPlayerNumber(PlayerNumber.PLAYER_TWO);

        assertEquals(List.of(Action.COOPERATE, Action.BETRAY, Action.NONE, Action.NONE, Action.NONE), playerOneHistory, "Player one's history should match the played actions.");
        assertEquals(List.of(Action.BETRAY, Action.COOPERATE, Action.NONE, Action.NONE, Action.NONE), playerTwoHistory, "Player two's history should match the played actions.");
    }


    @Test
    void testCalculateScoreAndGetScoreByTurnNumberAndByPlayerNumber(){
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
        int scorePlayerOneTurn0 = game.getScoreByTurnNumberAndByPlayerNumber(0, PlayerNumber.PLAYER_ONE);
        int scorePlayerTwoTurn0 = game.getScoreByTurnNumberAndByPlayerNumber(0, PlayerNumber.PLAYER_TWO);
        assertEquals( 0,scorePlayerOneTurn0);
        assertEquals( 5, scorePlayerTwoTurn0);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_ONE);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
        int scorePlayerOneTurn1 = game.getScoreByTurnNumberAndByPlayerNumber(1, PlayerNumber.PLAYER_ONE);
        int scorePlayerTwoTurn1 = game.getScoreByTurnNumberAndByPlayerNumber(1, PlayerNumber.PLAYER_TWO);
        assertEquals(5,scorePlayerOneTurn1);
        assertEquals( 0,scorePlayerTwoTurn1);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_ONE);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
        int scorePlayerOneTurn2 = game.getScoreByTurnNumberAndByPlayerNumber(2, PlayerNumber.PLAYER_ONE);
        int scorePlayerTwoTurn2 = game.getScoreByTurnNumberAndByPlayerNumber(2, PlayerNumber.PLAYER_TWO);
        assertEquals(1,scorePlayerOneTurn2);
        assertEquals(1,scorePlayerTwoTurn2);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
        int scorePlayerOneTurn3 = game.getScoreByTurnNumberAndByPlayerNumber(3, PlayerNumber.PLAYER_ONE);
        int scorePlayerTwoTurn3 = game.getScoreByTurnNumberAndByPlayerNumber(3, PlayerNumber.PLAYER_TWO);
        assertEquals( 3,scorePlayerOneTurn3);
        assertEquals( 3,scorePlayerTwoTurn3);

    }

    @Test
    void testGetAllScores(){
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_ONE);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
        List<Score> allScores = game.getAllScoresUntilCurrentTurn();
        Score scoreTurn0 = allScores.get(0);
        Score scoreTurn1 = allScores.get(1);
        Score scoreTurn2 = allScores.get(2);
        assertEquals(0,scoreTurn0.getScorePlayerOne());
        assertEquals(5,scoreTurn0.getScorePlayerTwo());
        assertEquals(5,scoreTurn1.getScorePlayerOne());
        assertEquals(0,scoreTurn1.getScorePlayerTwo());
        assertEquals(3,scoreTurn2.getScorePlayerOne());
        assertEquals(3,scoreTurn2.getScorePlayerTwo());

    }
}
