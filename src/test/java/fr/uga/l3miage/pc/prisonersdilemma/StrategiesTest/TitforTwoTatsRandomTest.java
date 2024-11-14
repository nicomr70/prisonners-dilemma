package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.TitforTwoTats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TitforTwoTatsRandomTest {
    private Random mockRandom;
    private TitforTwoTats strategy;

    @BeforeEach
    public void setup() {
        mockRandom = Mockito.mock(Random.class);
        strategy = new TitforTwoTats(mockRandom);
    }

    @Test
    public void testPlayWithInsufficientHistory() {
        List<Action> opponentHistory = new ArrayList<>();

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "TitforTwoTats should cooperate if the opponent's history is less than 2 actions.");

        opponentHistory.add(Action.BETRAY);
        action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "TitforTwoTats should still cooperate if the opponent's history has only one action.");
    }

    @Test
    public void testPlayWithTwoIdenticalLastActionsAndNonRandom() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.BETRAY);
        opponentHistory.add(Action.BETRAY);

        when(mockRandom.nextInt(2)).thenReturn(0);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "TitforTwoTats should mimic the last action if the opponent's last two actions are the same and isNextActionRandom is false.");
    }

    @Test
    public void testPlayWithTwoIdenticalLastActionsAndRandom() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);
        opponentHistory.add(Action.COOPERATE);

        when(mockRandom.nextInt(2)).thenReturn(1,0);


        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "TitforTwoTats should play BETRAY as a random action if isNextActionRandom is true.");
    }
    @Test
    public void testPlayWithTwoDifferentLastActions() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);
        opponentHistory.add(Action.BETRAY);

        when(mockRandom.nextInt(2)).thenReturn(1);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "TitforTwoTats should play a random action if the opponent's last two actions are different.");
    }
}
