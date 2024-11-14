package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.RandomStrategy;
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
public class RandomTest {

    private Random mockRandom;
    private RandomStrategy strategy;

    @BeforeEach
    public void setup() {
        mockRandom = Mockito.mock(Random.class);
        strategy = new RandomStrategy(mockRandom);
    }

    @Test
    public void testPlayReturnsCooperateWhenRandomIsOne() {
        List<Action> opponentHistory = new ArrayList<>();
        when(mockRandom.nextInt(2)).thenReturn(1);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "Random strategy should return COOPERATE when random is 1.");
    }

    @Test
    public void testPlayReturnsBetrayWhenRandomIsZero() {
        List<Action> opponentHistory = new ArrayList<>();
        when(mockRandom.nextInt(2)).thenReturn(0);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "Random strategy should return BETRAY when random is 0.");
    }

    @Test
    public void testPlayWithNonEmptyOpponentHistory() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);
        opponentHistory.add(Action.BETRAY);

        when(mockRandom.nextInt(2)).thenReturn(1);
        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "Random strategy should return COOPERATE when random is 1, regardless of opponent history.");

        when(mockRandom.nextInt(2)).thenReturn(0);
        action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "Random strategy should return BETRAY when random is 0, regardless of opponent history.");
    }
}
