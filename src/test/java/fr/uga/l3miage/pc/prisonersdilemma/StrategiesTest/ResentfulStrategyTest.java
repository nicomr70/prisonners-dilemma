package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.ResentfulStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ResentfulStrategyTest {

    private ResentfulStrategy strategy;

    @BeforeEach
    public void setup() {
        strategy = new ResentfulStrategy();
    }

    @Test
    public void testPlayWithEmptyOpponentHistory() {
        List<Action> opponentHistory = new ArrayList<>();

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "ResentfulStrategy should cooperate when opponent history is empty.");
    }

    @Test
    public void testPlayWhenOpponentCooperatedLast() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "ResentfulStrategy should cooperate if opponent's last action was COOPERATE.");
    }

    @Test
    public void testPlayWhenOpponentBetrayedLast() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.BETRAY);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "ResentfulStrategy should betray if opponent's last action was BETRAY.");
    }

    @Test
    public void testPlayWithMultipleActionsInOpponentHistory() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);
        opponentHistory.add(Action.COOPERATE);
        opponentHistory.add(Action.BETRAY);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "ResentfulStrategy should betray if the opponent betrayed in the last action, regardless of previous actions.");

        opponentHistory.add(Action.COOPERATE);

        action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "ResentfulStrategy should reset to cooperate if opponent's latest action is COOPERATE.");
    }
}
