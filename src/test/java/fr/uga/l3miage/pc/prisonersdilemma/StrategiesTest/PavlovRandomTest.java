package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.PavlovRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PavlovRandomTest {
    private PavlovRandom strategy;
    private Game game;

    private PlayerNumber opponent;
    private Random mockRandom;

    @BeforeEach
    public void setup() {

        game = new Game(5, null);
        opponent = PlayerNumber.PLAYER_ONE;
        mockRandom = mock(Random.class);
        strategy = new PavlovRandom(mockRandom);
    }

    @Test
    public void testInitialCooperation() {
        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "PavlovRandom should cooperate on the first move.");
    }

    @Test
    public void testCooperateIfLastScoreInsufficient() {
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(strategy.play(game, opponent), PlayerNumber.PLAYER_TWO);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "PavlovRandom should cooperate if the last score is insufficient.");
    }

    @Test
    public void testRepeatLastActionIfScoreSufficient() {
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "PavlovRandom should repeat the last action if the score is sufficient.");
    }

    @Test
    public void testRandomActionTriggered() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
        when(mockRandom.nextInt(2)).thenReturn(1,0);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "PavlovRandom should betray if the random action is triggered and betrayal is chosen.");
    }

    @Test
    public void testRepeatLastActionIfRandomNotTriggered() {
        when(game.getScoreByTurnNumberAndByPlayerNumber(0, PlayerNumber.PLAYER_TWO)).thenReturn(3);
        when(mockRandom.nextInt(2)).thenReturn(0);

        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "PavlovRandom should repeat the last action if no random action is triggered.");
    }

    @Test
    public void testRandomActionCooperation() {
        // Simuler une action aléatoire : coopération
        when(game.getScoreByTurnNumberAndByPlayerNumber(0, PlayerNumber.PLAYER_TWO)).thenReturn(3);
        when(mockRandom.nextInt(2)).thenReturn(1, 1); // Déclencher une action aléatoire, puis coopération

        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "PavlovRandom should cooperate if random action is triggered and cooperation is chosen.");
    }

    @Test
    public void testMultipleTurnsWithRandomness() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
        when(mockRandom.nextInt(2)).thenReturn(1, 0);

        Action action1 = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action1, "PavlovRandom should cooperate on random action.");

        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(action1, PlayerNumber.PLAYER_TWO);
        Action action2 = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action2, "PavlovRandom should continue to cooperate if random action is triggered.");
    }
}
