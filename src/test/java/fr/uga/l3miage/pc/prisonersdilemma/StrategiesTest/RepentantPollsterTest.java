package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.RepentantPollster;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.Utils;
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
class RepentantPollsterTest {

    private Random mockRandom;
    private RepentantPollster strategy;
    private Game game;
    private WebSocketSession mockSession;
    private PlayerNumber opponent;

    @BeforeEach
     void setup() {
        mockSession = mock(WebSocketSession.class);
        game = new Game(5, mockSession);
        opponent = PlayerNumber.PLAYER_ONE;
        mockRandom = Mockito.mock(Random.class);
        strategy = new RepentantPollster(mockRandom);
    }

    @Test
     void testPlayWithEmptyHistory() {
        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "RepentantPollster should cooperate on the first move.");
    }

    @Test
     void testPlayWithRandomBetray() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, Utils.getStrategyPlayerNumber(opponent));

        when(mockRandom.nextInt(2)).thenReturn(1); // Force le comportement à trahir aléatoirement

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "RepentantPollster should betray randomly if isNextActionRandom() is true.");
    }

    @Test
     void testPlayWithOpponentBetrayAfterRandomBetray() {
        game.playTurn(Action.BETRAY, Utils.getStrategyPlayerNumber(opponent));
        game.playTurn(Action.COOPERATE, opponent);

        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.COOPERATE, Utils.getStrategyPlayerNumber(opponent));

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "RepentantPollster should cooperate if the opponent betrays after a random betray.");
    }

    @Test
     void testPlayWithTitForTatBehavior() {
        game.playTurn(Action.COOPERATE, Utils.getStrategyPlayerNumber(opponent));
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, Utils.getStrategyPlayerNumber(opponent));
        game.playTurn(Action.COOPERATE, opponent);

        when(mockRandom.nextInt(2)).thenReturn(0); // Force l'action à imiter (pas de trahison aléatoire)

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "RepentantPollster should cooperate if the opponent cooperated last and no random betray is triggered.");
    }

    @Test
     void testPlayWithOpponentLastActionImitation() {
        game.playTurn(Action.COOPERATE, Utils.getStrategyPlayerNumber(opponent));
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.COOPERATE, Utils.getStrategyPlayerNumber(opponent));
        game.playTurn(Action.BETRAY, opponent);

        when(mockRandom.nextInt(2)).thenReturn(0); // Force l'action à imiter

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "RepentantPollster should mimic the opponent's last action if no random betray is triggered.");
    }
}

