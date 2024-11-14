package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.RepentantPollster;
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
public class RepentantPollsterTest {

    private Random mockRandom;
    private RepentantPollster strategy;

    @BeforeEach
    public void setup() {
        mockRandom = Mockito.mock(Random.class);
        strategy = new RepentantPollster(mockRandom);
    }

    @Test
    public void testPlayWithEmptyHistory() {
        List<Action> opponentHistory = new ArrayList<>();

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "RepentantPollster should cooperate on the first move.");
    }

    @Test
    public void testPlayWithRandomBetray() {
        List<Action> opponentHistory = new ArrayList<>();
        strategy.getStrategyHistory().add(Action.COOPERATE);
        opponentHistory.add(Action.COOPERATE);

        when(mockRandom.nextInt(2)).thenReturn(1); // Force le comportement à trahir aléatoirement

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "RepentantPollster should betray randomly if isNextActionRandom() is true.");
    }

    @Test
    public void testPlayWithOpponentBetrayAfterRandomBetray() {
        List<Action> opponentHistory = new ArrayList<>();
        strategy.getStrategyHistory().add(Action.COOPERATE);
        opponentHistory.add(Action.COOPERATE);

        // Trahir aléatoirement
        when(mockRandom.nextInt(2)).thenReturn(1);
        strategy.play(opponentHistory); // Le premier coup est une trahison aléatoire

        // L'adversaire trahit après la trahison
        opponentHistory.add(Action.BETRAY);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "RepentantPollster should cooperate if the opponent betrays after a random betray.");
    }

    @Test
    public void testPlayWithTitForTatBehavior() {
        List<Action> opponentHistory = new ArrayList<>();
        strategy.getStrategyHistory().add(Action.COOPERATE);
        opponentHistory.add(Action.COOPERATE);
        strategy.getStrategyHistory().add(Action.COOPERATE);
        opponentHistory.add(Action.COOPERATE);

        when(mockRandom.nextInt(2)).thenReturn(0); // Force l'action à imiter (pas de trahison aléatoire)

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "RepentantPollster should cooperate if the opponent cooperated last and no random betray is triggered.");
    }

    @Test
    public void testPlayWithOpponentLastActionImitation() {
        List<Action> opponentHistory = new ArrayList<>();
        strategy.getStrategyHistory().add(Action.COOPERATE);
        opponentHistory.add(Action.BETRAY);
        strategy.getStrategyHistory().add(Action.COOPERATE);
        opponentHistory.add(Action.BETRAY);

        when(mockRandom.nextInt(2)).thenReturn(0); // Force l'action à imiter

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "RepentantPollster should mimic the opponent's last action if no random betray is triggered.");
    }
}

