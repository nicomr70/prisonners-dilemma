package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.Peacemaker;
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
public class PeacemakerTest {

    private Random mockRandom;
    private Peacemaker strategy;

    @BeforeEach
    public void setup() {
        mockRandom = Mockito.mock(Random.class);
        strategy = new Peacemaker(mockRandom);
    }

    @Test
    public void testPlayWithInsufficientHistory() {
        List<Action> opponentHistory = new ArrayList<>();

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "Peacemaker should cooperate if the opponent's history is less than 2 actions.");

        opponentHistory.add(Action.BETRAY);
        action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "Peacemaker should still cooperate if the opponent's history has only one action.");
    }

    @Test
    public void testPlayWhenOpponentBetrayedTwiceInARowAndPeaceTurn() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.BETRAY);
        opponentHistory.add(Action.BETRAY);

        when(mockRandom.nextInt(2)).thenReturn(1);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "Peacemaker should cooperate on a 'Peace Turn' even if opponent betrayed twice in a row.");
    }

    @Test
    public void testPlayWhenOpponentBetrayedTwiceInARowAndNotPeaceTurn() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.BETRAY);
        opponentHistory.add(Action.BETRAY);

        when(mockRandom.nextInt(2)).thenReturn(0);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "Peacemaker should betray if opponent betrayed twice in a row and it's not a 'Peace Turn'.");
    }

    @Test
    public void testPlayWhenOpponentDidNotBetrayTwiceInARow() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);
        opponentHistory.add(Action.BETRAY);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "Peacemaker should cooperate if the opponent did not betray twice in a row.");
    }

    @Test
    public void testPlayWithMixedOpponentActions() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);
        opponentHistory.add(Action.COOPERATE);
        opponentHistory.add(Action.BETRAY);
        opponentHistory.add(Action.COOPERATE);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "Peacemaker should cooperate with mixed opponent actions, as there's no two consecutive betrayals.");
    }
}
