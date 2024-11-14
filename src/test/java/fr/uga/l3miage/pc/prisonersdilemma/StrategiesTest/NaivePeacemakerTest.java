package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.NaivePeacemaker;
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
public class NaivePeacemakerTest {

    private Random mockRandom;
    private NaivePeacemaker strategy;

    @BeforeEach
    public void setup() {
        mockRandom = Mockito.mock(Random.class);
        strategy = new NaivePeacemaker(mockRandom);
    }

    @Test
    public void testPlayWithEmptyOpponentHistory() {
        List<Action> opponentHistory = new ArrayList<>();

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "NaivePassificator should start with COOPERATE on an empty opponent history.");
    }

    @Test
    public void testPlayWhenOpponentBetrayedAndNextActionIsCooperate() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.BETRAY);

        // Forcer NaivePassificator à coopérer après une trahison de l'adversaire
        when(mockRandom.nextInt(2)).thenReturn(1); // isNextActionCooperate() retourne true

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "NaivePassificator should cooperate after opponent's betrayal if random check allows.");
    }

    @Test
    public void testPlayWhenOpponentBetrayedAndNextActionIsNotCooperate() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.BETRAY);

        // Forcer NaivePassificator à imiter l'action de trahison de l'adversaire
        when(mockRandom.nextInt(2)).thenReturn(0); // isNextActionCooperate() retourne false

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "NaivePassificator should mimic the opponent's betrayal if random check disallows cooperation.");
    }

    @Test
    public void testPlayMimicsLastOpponentActionWhenNoBetrayal() {
        List<Action> opponentHistory = new ArrayList<>();
        opponentHistory.add(Action.COOPERATE);

        Action action = strategy.play(opponentHistory);
        assertEquals(Action.COOPERATE, action, "NaivePassificator should mimic the last opponent action when the last action was cooperate.");

        // Ajouter une autre action pour tester l'imitation
        opponentHistory.add(Action.BETRAY);
        action = strategy.play(opponentHistory);
        assertEquals(Action.BETRAY, action, "NaivePassificator should mimic the last opponent action when opponent betrays.");
    }
}

