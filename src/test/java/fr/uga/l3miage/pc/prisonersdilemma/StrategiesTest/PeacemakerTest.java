package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.Peacemaker;
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
public class PeacemakerTest {

    private Random mockRandom;
    private Peacemaker strategy;
    private Game game;
    private WebSocketSession mockSession;
    private PlayerNumber opponent;

    @BeforeEach
    public void setup() {
        mockSession = mock(WebSocketSession.class);
        game = new Game(5, mockSession);
        mockRandom = Mockito.mock(Random.class);
        strategy = new Peacemaker(mockRandom);
        opponent = PlayerNumber.PLAYER_ONE;
    }

    @Test
    public void testPlayWithInsufficientHistory() {
        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "Peacemaker should cooperate if the opponent's history is less than 2 actions.");

        game.playTurn(Action.BETRAY, opponent);

        action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "Peacemaker should still cooperate if the opponent's history has only one action.");
    }

    @Test
    public void testPlayWhenOpponentBetrayedTwiceInARowAndPeaceTurn() {
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.BETRAY, opponent);

        when(mockRandom.nextInt(2)).thenReturn(1);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "Peacemaker should cooperate on a 'Peace Turn' even if opponent betrayed twice in a row.");
    }

    @Test
    public void testPlayWhenOpponentBetrayedTwiceInARowAndNotPeaceTurn() {
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
        game.playTurn(Action.BETRAY, opponent);

        when(mockRandom.nextInt(2)).thenReturn(0);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "Peacemaker should betray if opponent betrayed twice in a row and it's not a 'Peace Turn'.");
    }

    @Test
    public void testPlayWhenOpponentDidNotBetrayTwiceInARow() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.BETRAY, opponent);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "Peacemaker should cooperate if the opponent did not betray twice in a row.");
    }

    @Test
    public void testPlayWithMixedOpponentActions() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.COOPERATE, opponent);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "Peacemaker should cooperate with mixed opponent actions, as there's no two consecutive betrayals.");
    }
}
