package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.TitforTatRandom;
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
public class TitforTatRandomTest {

    private Random mockRandom;
    private TitforTatRandom strategy;

    @BeforeEach
    public void setup() {
        mockRandom = Mockito.mock(Random.class);
        strategy = new TitforTatRandom(mockRandom);
    }

    @Test
    public void testPlayWithEmptyHistory() {
        List<Action> opponentHistory = new ArrayList<>();

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "TitforTatRandom should cooperate on the first move.");
    }

    @Test
    public void testPlayWithRandomAction() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);

        when(mockRandom.nextInt(2)).thenReturn(1);
        when(mockRandom.nextInt(2)).thenReturn(1);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "TitforTatRandom should randomly choose to cooperate.");
    }

    @Test
    public void testPlayWithTitForTatBehavior() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.BETRAY);

        when(mockRandom.nextInt(2)).thenReturn(0);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "TitforTatRandom should mimic the opponent's last action (Tit for Tat).");
    }
}




