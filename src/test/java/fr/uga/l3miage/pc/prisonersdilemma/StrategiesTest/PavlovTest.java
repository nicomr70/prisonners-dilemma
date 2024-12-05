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
class PavlovTest {
    private Pavlov strategy;
    private Game game;
    private PlayerNumber opponent;
    private PlayerNumber strategyPlayernumber;

    @BeforeEach
     void setup() {
        game = new Game(5, null);
        strategy = new Pavlov();
        opponent = PlayerNumber.PLAYER_ONE;
        strategyPlayernumber = PlayerNumber.PLAYER_TWO;
    }

    @Test
    void testEmptyHistory(){
        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action);
    }

    @Test
    void testSufficientScoreBetray(){
        game.playTurn(Action.COOPERATE,opponent);
        game.playTurn(Action.BETRAY, strategyPlayernumber);
        Action action = strategy.play(game, opponent);
        assertEquals( Action.BETRAY, action);
    }

    @Test
    void testSufficientScoreCooperate(){
        game.playTurn(Action.COOPERATE,opponent);
        game.playTurn(Action.COOPERATE, strategyPlayernumber);
        Action action = strategy.play(game, opponent);
        assertEquals( Action.COOPERATE,action);
    }

    @Test
    void testInsufficientSufficientScoreCooperate(){
        game.playTurn(Action.BETRAY,opponent);
        game.playTurn(Action.COOPERATE, strategyPlayernumber);
        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action);
    }

    @Test
    void testInsufficientSufficientScoreBetray(){
        game.playTurn(Action.BETRAY,opponent);
        game.playTurn(Action.BETRAY, strategyPlayernumber);
        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action);
    }


    @Test
    void testMultipleInstances(){
        game.playTurn(Action.COOPERATE,opponent);
        game.playTurn(Action.COOPERATE, strategyPlayernumber);
        Action action = strategy.play(game, opponent);
        assertEquals( Action.COOPERATE,action);
        game.playTurn(Action.COOPERATE,opponent);
        game.playTurn(Action.BETRAY, strategyPlayernumber);
        Action actionTurn1 = strategy.play(game, opponent);
        assertEquals( Action.BETRAY,actionTurn1);
        game.playTurn(Action.BETRAY,opponent);
        game.playTurn(Action.BETRAY, strategyPlayernumber);
        Action actionTurn2 = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, actionTurn2);
    }
}
