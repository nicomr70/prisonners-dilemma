package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.TitforTwoTatsRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.socket.WebSocketSession;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TitforTwoTatsRandomTest {
    private Random mockRandom;
    private TitforTwoTatsRandom strategy;
    private Game game;
    private WebSocketSession mockSession;
    private PlayerNumber opponent;

    @BeforeEach
    public void setup() {
        mockRandom = Mockito.mock(Random.class);
        strategy = new TitforTwoTatsRandom(mockRandom);
        mockSession = mock(WebSocketSession.class);
        game = new Game(5, mockSession);
        opponent = PlayerNumber.PLAYER_ONE;
    }

    @Test
    public void testPlayWithInsufficientHistory() {
        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "TitforTwoTats should cooperate if the opponent's history is less than 2 actions.");

        game.playTurn(Action.BETRAY, opponent);
        action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "TitforTwoTats should still cooperate if the opponent's history has only one action.");
    }

    @Test
    public void testPlayWithTwoIdenticalLastActionsAndNonRandom() {
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.BETRAY, opponent);

        when(mockRandom.nextInt(2)).thenReturn(0);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "TitforTwoTats should mimic the last action if the opponent's last two actions are the same and isNextActionRandom is false.");
    }

    @Test
    public void testPlayWithTwoIdenticalLastActionsAndRandom() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, opponent);

        when(mockRandom.nextInt(2)).thenReturn(1,0);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "TitforTwoTats should play BETRAY as a random action if isNextActionRandom is true.");
    }
    @Test
    public void testPlayWhenOpponentHasMixedLastActions() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.BETRAY, opponent);

        when(mockRandom.nextInt(2)).thenReturn(0);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "TitforTwoTats should BETRAY when opponent's last two actions are mixed and reciprocity starts.");
    }

    @Test
    public void testPlayWithStartReciprocityAndNonRandom() {
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.BETRAY, opponent);

        when(mockRandom.nextInt(2)).thenReturn(0);

        // Déclencher la réciprocité
        strategy.play(game, opponent);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "TitforTwoTats should mimic the opponent's last action when startReciprocity is true and isNextActionRandom is false.");
    }

    @Test
    public void testPlayWithStartReciprocityAndRandom() {
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.BETRAY, opponent);

        when(mockRandom.nextInt(2)).thenReturn(1, 0);

        // Déclencher la réciprocité
        strategy.play(game, opponent);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "TitforTwoTats should COOPERATE as a random action even if startReciprocity is true.");
    }

    @Test
    public void testPlayWithRandomBehavior() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, opponent);

        when(mockRandom.nextInt(2)).thenReturn(1);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "TitforTwoTats should COOPERATE as a random action.");
    }

    @Test
    public void testPlayWithOpponentCooperatingTwice() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, opponent);

        when(mockRandom.nextInt(2)).thenReturn(0);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "TitforTwoTats should mimic COOPERATE when opponent cooperates twice and isNextActionRandom is false.");
    }
}


