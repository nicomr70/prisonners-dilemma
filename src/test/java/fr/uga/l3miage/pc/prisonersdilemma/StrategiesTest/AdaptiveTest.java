package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.Adaptive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class AdaptiveTest {


    private Adaptive strategy;
    private Game game;
    private PlayerNumber opponent;

    @BeforeEach
    public void setup() {
        game = mock(Game.class);
        strategy = new Adaptive();
        opponent = PlayerNumber.PLAYER_ONE;
    }

    @Test
    public void testInitialCooperationSequence() {
        for (int turn = 0; turn < 6; turn++) {
            when(game.getCurrentTurn()).thenReturn(turn);
            Action action = strategy.play(game, opponent);
            assertEquals(Action.COOPERATE, action, "Adaptive should cooperate during the initial sequence (turn " + turn + ").");
        }
    }

    @Test
    public void testInitialBetraySequence() {
        for (int turn = 6; turn < 10; turn++) {
            when(game.getCurrentTurn()).thenReturn(turn);
            Action action = strategy.play(game, opponent);
            assertEquals(Action.BETRAY, action, "Adaptive should betray during the initial betrayal sequence (turn " + turn + ").");
        }
    }

    @Test
    public void testActionBasedOnBestMean_CooperatePreferred() {
        for(int i = 0; i < 6; i++){
            game.playTurn(Action.COOPERATE,opponent);
            game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
        }
        for(int i = 0; i < 4; i++){
            game.playTurn(Action.BETRAY,opponent);
            game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
        }

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "Adaptive should prefer cooperation if it has a better mean score.");
    }

    @Test
    public void testActionBasedOnBestMean_BetrayPreferred() {
        when(game.getCurrentTurn()).thenReturn(10);

        when(game.getScoreByTurnNumberAndByPlayerNumber(anyInt(), any())).thenAnswer(invocation -> {
            int turn = invocation.getArgument(0);
            return (turn < 6) ? 2 : 5;
        });

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "Adaptive should prefer betrayal if it has a better mean score.");
    }

    @Test
    public void testEdgeCase_InsufficientTurns() {
        when(game.getCurrentTurn()).thenReturn(5);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "Adaptive should cooperate if the current turn is within the initial sequence.");
    }


}
