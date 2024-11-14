package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.PollsterRandomBetray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PollsterRandomBetrayTest {
    private Random mockRandom;
    private PollsterRandomBetray strategy;

    @BeforeEach
    public void setup() {
        mockRandom = Mockito.mock(Random.class);
        strategy = new PollsterRandomBetray(mockRandom);
    }

    @Test
    public void testPlayWithEmptyHistory() {
        List<Action> opponentHistory = new ArrayList<>();

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "PollsterRandomBetray should cooperate if the opponent's history is empty.");
    }

    @Test
    public void testPlayWithRandomBetray() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);

        when(mockRandom.nextInt(2)).thenReturn(1);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "PollsterRandomBetray should betray when isNextActionBetray is true.");
    }

    @Test
    public void testPlayWithTitForTatBehavior() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.BETRAY);

        when(mockRandom.nextInt(2)).thenReturn(0);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "PollsterRandomBetray should mimic the last action if isNextActionBetray is false.");
    }

    @Test
    public void testPlayWithOpponentCooperateAndRandomCooperate() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);

        when(mockRandom.nextInt(2)).thenReturn(0);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "PollsterRandomBetray should mimic the opponent's last action when isNextActionBetray is false.");
    }
}
