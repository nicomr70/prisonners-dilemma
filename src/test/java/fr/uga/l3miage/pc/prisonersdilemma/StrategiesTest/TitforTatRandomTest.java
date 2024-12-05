package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.TitforTatRandom;
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
class TitforTatRandomTest {

    private Random mockRandom;
    private TitforTatRandom strategy;
    private Game game;
    private WebSocketSession mockSession;
    private PlayerNumber opponent;

    @BeforeEach
     void setup() {
        mockSession = mock(WebSocketSession.class);
        game = new Game(5, mockSession);
        opponent = PlayerNumber.PLAYER_ONE;
        mockRandom = Mockito.mock(Random.class);
        strategy = new TitforTatRandom(mockRandom);
    }

    @Test
     void testPlayWithEmptyHistory() {
        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "TitforTatRandom should cooperate on the first move.");
    }

    @Test
     void testPlayWithRandomAction() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);


        when(mockRandom.nextInt(2)).thenReturn(1);
        when(mockRandom.nextInt(2)).thenReturn(1);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "TitforTatRandom should randomly choose to cooperate.");
    }

    @Test
     void testPlayWithTitForTatBehavior() {
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);


        when(mockRandom.nextInt(2)).thenReturn(0);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.BETRAY, action, "TitforTatRandom should mimic the opponent's last action (Tit for Tat).");
    }
}




