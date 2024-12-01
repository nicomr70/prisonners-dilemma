package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.ResentfulStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class ResentfulStrategyTest {

    private ResentfulStrategy strategy;
    private Game game;
    private WebSocketSession mockSession;
    private PlayerNumber opponent;

    @BeforeEach
    public void setup() {
        mockSession = mock(WebSocketSession.class);
        game = new Game(5, mockSession);
        opponent = PlayerNumber.PLAYER_ONE;
        strategy = new ResentfulStrategy();
    }

    @Test
    public void testPlayWithEmptyOpponentHistory() {
        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "ResentfulStrategy should cooperate when opponent history is empty.");
    }

    @Test
    public void testPlayWhenOpponentCooperatedLast() {
        game.playTurn(Action.COOPERATE, opponent);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "ResentfulStrategy should cooperate if opponent's last action was COOPERATE.");
    }

    @Test
    public void testPlayWhenOpponentBetrayedLast() {
        game.playTurn(Action.BETRAY, opponent);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "ResentfulStrategy should betray if opponent's last action was BETRAY.");
    }

    @Test
    public void testPlayWithMultipleActionsInOpponentHistory() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.BETRAY, opponent);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "ResentfulStrategy should betray if the opponent betrayed in the last action, regardless of previous actions.");

        game.playTurn(Action.COOPERATE, opponent);

        action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "ResentfulStrategy should reset to cooperate if opponent's latest action is COOPERATE.");
    }
}
