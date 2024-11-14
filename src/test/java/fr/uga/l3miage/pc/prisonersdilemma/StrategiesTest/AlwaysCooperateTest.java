package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.AlwaysCooperate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AlwaysCooperateTest {

    private AlwaysCooperate strategy;

    @BeforeEach
    public void setup() {
        strategy = new AlwaysCooperate();
    }

    @Test
    public void testPlayWithEmptyOpponentHistory() {
        List<Action> opponentHistory = new ArrayList<>();
        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "AlwaysBetray should always return BETRAY, even with empty opponent history.");
    }

    @Test
    public void testPlayWithSingleActionInOpponentHistory() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "AlwaysBetray should always return BETRAY, regardless of opponent's history.");
    }

    @Test
    public void testPlayWithMultipleActionsInOpponentHistory() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);
        opponentHistory.add(Action.BETRAY);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "AlwaysBetray should always return BETRAY, regardless of opponent's history.");
    }
}

