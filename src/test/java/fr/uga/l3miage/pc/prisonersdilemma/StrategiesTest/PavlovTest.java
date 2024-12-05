package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.Pavlov;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class PavlovTest {
    private Pavlov strategy;
    private Game game;
    private PlayerNumber opponent;
    private PlayerNumber strategyPlayernumber;

    @BeforeEach
    public void setup() {
        game = new Game(5, null);
        strategy = new Pavlov();
        opponent = PlayerNumber.PLAYER_ONE;
        strategyPlayernumber = PlayerNumber.PLAYER_TWO;
    }

    @Test
    void testEmptyHistory(){
        Action action = strategy.play(game, opponent);
        assertEquals(action, Action.COOPERATE);
    }

    @Test
    void testSufficientScoreBetray(){
        game.playTurn(Action.COOPERATE,opponent);
        game.playTurn(Action.BETRAY, strategyPlayernumber);
        Action action = strategy.play(game, opponent);
        assertEquals(action, Action.BETRAY);
    }

    @Test
    void testSufficientScoreCooperate(){
        game.playTurn(Action.COOPERATE,opponent);
        game.playTurn(Action.COOPERATE, strategyPlayernumber);
        Action action = strategy.play(game, opponent);
        assertEquals(action, Action.COOPERATE);
    }

    @Test
    void testInsufficientSufficientScoreCooperate(){
        game.playTurn(Action.BETRAY,opponent);
        game.playTurn(Action.COOPERATE, strategyPlayernumber);
        Action action = strategy.play(game, opponent);
        assertEquals(action, Action.COOPERATE);
    }

    @Test
    void testInsufficientSufficientScoreBetray(){
        game.playTurn(Action.BETRAY,opponent);
        game.playTurn(Action.BETRAY, strategyPlayernumber);
        Action action = strategy.play(game, opponent);
        assertEquals(action, Action.COOPERATE);
    }


    @Test
    void testMultipleInstances(){
        game.playTurn(Action.COOPERATE,opponent);
        game.playTurn(Action.COOPERATE, strategyPlayernumber);
        Action action = strategy.play(game, opponent);
        assertEquals(action, Action.COOPERATE);
        game.playTurn(Action.COOPERATE,opponent);
        game.playTurn(Action.BETRAY, strategyPlayernumber);
        Action actionTurn1 = strategy.play(game, opponent);
        assertEquals(actionTurn1, Action.BETRAY);
        game.playTurn(Action.BETRAY,opponent);
        game.playTurn(Action.BETRAY, strategyPlayernumber);
        Action actionTurn2 = strategy.play(game, opponent);
        assertEquals(actionTurn2, Action.COOPERATE);
    }
}
