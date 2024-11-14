package fr.uga.l3miage.pc.prisonersdilemma;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;
import fr.uga.l3miage.pc.prisonersdilemma.game.states.State;
import fr.uga.l3miage.pc.prisonersdilemma.game.states.WaitingState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
public class GameTest {
    private Game game;
    private State mockState;

    @BeforeEach
    void setUp() {
        game = new Game(5); // Initialize a game with 5 turns
        mockState = mock(State.class); // Create a mock state for testing
        game.changeState(mockState); // Set the game to use the mock state
    }

    @Test
    void testGenerateGameId() {
        assertNotNull(game.getId(), "Game ID should not be null after initialization.");
    }

    @Test
    void testInitialStateIsWaiting() {
        Game newGame = new Game(5); // Create a new game instance
        assertTrue(newGame.getState() instanceof WaitingState, "Initial state should be WaitingState.");
    }

    @Test
    void testChangeState() {
        game.changeState(mockState);
        assertEquals(mockState, game.getState(), "The game state should be changed to the mock state.");
    }

    @Test
    void testPlayDelegatesToState() {
        game.play(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
        verify(mockState, times(1)).play(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
    }

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
        assertEquals(0, game.getCurrentTurn(), "Current turn should not increment after Player Two's action.");

        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
        assertEquals(1, game.getCurrentTurn(), "Current turn should increment by 1 after Player One's action.");
    }

//    @Test
//    void testGetHistoryByPlayerNumberReturnsCorrectActions() {
//        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
//        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
//        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_ONE);
//        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
//
//        List<Action> playerOneHistory = game.getHistoryByPlayerNumber(PlayerNumber.PLAYER_ONE);
//        List<Action> playerTwoHistory = game.getHistoryByPlayerNumber(PlayerNumber.PLAYER_TWO);
//
//        assertEquals(List.of(Action.COOPERATE, Action.BETRAY), playerOneHistory, "Player one's history should match the played actions.");
//        assertEquals(List.of(Action.BETRAY, Action.COOPERATE), playerTwoHistory, "Player two's history should match the played actions.");
//    }}
}