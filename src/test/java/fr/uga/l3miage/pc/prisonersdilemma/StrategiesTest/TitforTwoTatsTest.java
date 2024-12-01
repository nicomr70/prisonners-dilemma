package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.TitforTwoTats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TitforTwoTatsTest {

    private TitforTwoTats strategy;
    private Game game;
    private WebSocketSession mockSession;
    private PlayerNumber opponent;

    @BeforeEach
    public void setup() {
        mockSession = mock(WebSocketSession.class);
        game = new Game(5, mockSession);
        opponent = PlayerNumber.PLAYER_ONE;
        strategy = new TitforTwoTats();
    }

    @Test
    public void testPlayWithInsufficientHistory() {
        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "TitforTwoTats should cooperate if the opponent's history is less than 2 actions.");

        game.playTurn(Action.BETRAY, opponent);
        action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "TitforTwoTats should still cooperate if the opponent's history has only one action.");
    }
    @Test
    public void testPlayWhenOpponentHasMixedLastActions() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.BETRAY, opponent);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "TitforTwoTats should BETRAY when the opponent's last two actions are mixed, and reciprocity has not started.");
    }

    @Test
    public void testPlayWithTwoIdenticalLastActionsAndTriggerReciprocity() {
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.BETRAY, opponent);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "TitforTwoTats should BETRAY and trigger reciprocity when the opponent's last two actions are identical.");

        game.playTurn(Action.COOPERATE, opponent);
        action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "TitforTwoTats should mimic the opponent's last action after reciprocity has started.");
    }

    @Test
    public void testPlayWhenReciprocityAlreadyStarted() {
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.BETRAY, opponent);

        strategy.play(game, opponent);

        game.playTurn(Action.COOPERATE, opponent);
        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "TitforTwoTats should continue to mimic the opponent's actions after reciprocity has started.");

        game.playTurn(Action.BETRAY, opponent);
        action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "TitforTwoTats should continue to mimic the opponent's betrayal after reciprocity has started.");
    }

    @Test
    public void testPlayWhenOpponentCooperatesTwice() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, opponent);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "TitforTwoTats should COOPERATE when the opponent cooperates twice.");
    }

    @Test
    public void testPlayWithLongHistoryAndMixedActions() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.BETRAY, opponent);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "TitforTwoTats should BETRAY when the opponent's last action is BETRAY, regardless of earlier history.");
    }

}
