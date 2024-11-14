package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.TitforTat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TitforTatTest {
    private final TitforTat strategy = new TitforTat();

    @Test
    public void testPlayWithEmptyHistory() {
        List<Action> opponentHistory = new ArrayList<>();

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "TitforTat should cooperate on the first move.");
    }

    @Test
    public void testPlayWithOpponentLastActionCooperate() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.BETRAY);
        opponentHistory.add(Action.COOPERATE);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "TitforTat should cooperate if the opponent cooperated last.");
    }

    @Test
    public void testPlayWithOpponentLastActionBetray() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);
        opponentHistory.add(Action.BETRAY);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "TitforTat should betray if the opponent betrayed last.");
    }
}
