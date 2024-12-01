package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.NaivePeacemaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.WebSocketSession;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class NaivePeacemakerTest {

    private Random mockRandom;
    private NaivePeacemaker strategy;
    private Game game;
    private WebSocketSession mockSession;
    private PlayerNumber opponent;

    @BeforeEach
    public void setup() {
        mockSession = mock(WebSocketSession.class);
        game = new Game(5, mockSession);
        mockRandom = Mockito.mock(Random.class);
        strategy = new NaivePeacemaker(mockRandom);
        opponent = PlayerNumber.PLAYER_ONE;
    }

    @Test
    public void testPlayWithEmptyOpponentHistory() {
        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "NaivePassificator should start with COOPERATE on an empty opponent history.");
    }

    @Test
    public void testPlayWhenOpponentBetrayedAndNextActionIsCooperate() {
        game.playTurn(Action.BETRAY, opponent);
        when(mockRandom.nextInt(2)).thenReturn(1);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "NaivePassificator should cooperate after opponent's betrayal if random check allows.");
    }

    @Test
    public void testPlayWhenOpponentBetrayedAndNextActionIsNotCooperate() {
        game.playTurn(Action.BETRAY, opponent);

        when(mockRandom.nextInt(2)).thenReturn(0);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "NaivePassificator should mimic the opponent's betrayal if random check disallows cooperation.");
    }

    @Test
    public void testPlayMimicsLastOpponentActionWhenNoBetrayal() {
        game.playTurn(Action.COOPERATE, opponent);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "NaivePassificator should mimic the last opponent action when the last action was cooperate.");

        game.playTurn(Action.BETRAY, opponent);

        action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "NaivePassificator should mimic the last opponent action when opponent betrays.");
    }
}

