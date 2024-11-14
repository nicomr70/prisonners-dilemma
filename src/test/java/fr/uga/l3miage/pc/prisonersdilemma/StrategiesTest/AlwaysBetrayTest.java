package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.AlwaysBetray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AlwaysBetrayTest {

    private AlwaysBetray strategy;

    @BeforeEach
    public void setup() {
        strategy = new AlwaysBetray();
    }

    @Test
    public void testPlayWithEmptyOpponentHistory() {
        List<Action> opponentHistory = new ArrayList<>();
        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "AlwaysBetray should always return BETRAY, even with empty opponent history.");
    }

    @Test
    public void testPlayWithSingleActionInOpponentHistory() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "AlwaysBetray should always return BETRAY, regardless of opponent's history.");
    }

    @Test
    public void testPlayWithMultipleActionsInOpponentHistory() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);
        opponentHistory.add(Action.BETRAY);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "AlwaysBetray should always return BETRAY, regardless of opponent's history.");
    }
}

