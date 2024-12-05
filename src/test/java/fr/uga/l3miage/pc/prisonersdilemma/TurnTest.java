package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TurnTest {

    private Turn turn;

    @BeforeEach
    void setUp(){
        turn = new Turn(Action.NONE,Action.NONE);
    }

    @Test
    void testCalculateScoreTurnNotFinished(){
        turn.calculateScore();
        assertEquals(0,turn.getScorePlayerOne(),"calculateScore should not change the score if the turn is not finished");
        assertEquals(0,turn.getScorePlayerTwo(),"calculateScore should not change the score if the turn is not finished");
    }

    @Test
    void testCalculateScoreBothPlayerCooperate(){
        turn.updateTurn(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
        turn.updateTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
        turn.calculateScore();
        assertEquals(3,turn.getScorePlayerOne(),"calculateScore should change the score to 3 for the player one score");
        assertEquals(3,turn.getScorePlayerTwo(),"calculateScore should change the score to 3 for the player two score");

    }

    @Test
    void testCalculateScorePlayerOneBetrayPlayerTwoCooperate(){
        turn.updateTurn(Action.BETRAY, PlayerNumber.PLAYER_ONE);
        turn.updateTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
        turn.calculateScore();
        assertEquals(5,turn.getScorePlayerOne(),"calculateScore should change the score to 3 for the player one score");
        assertEquals(0,turn.getScorePlayerTwo(),"calculateScore should change the score to 3 for the player two score");
    }

    @Test
    void testCalculateScorePlayerOneCooperatePlayerTwoBetray(){
        turn.updateTurn(Action.COOPERATE, PlayerNumber.PLAYER_ONE);
        turn.updateTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
        turn.calculateScore();
        assertEquals(0,turn.getScorePlayerOne(),"calculateScore should change the score to 3 for the player one score");
        assertEquals(5,turn.getScorePlayerTwo(),"calculateScore should change the score to 3 for the player two score");
    }

    @Test
    void testCalculateScoreBothBetray(){
        turn.updateTurn(Action.BETRAY, PlayerNumber.PLAYER_ONE);
        turn.updateTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
        turn.calculateScore();
        assertEquals(1,turn.getScorePlayerOne(),"calculateScore should change the score to 3 for the player one score");
        assertEquals(1,turn.getScorePlayerTwo(),"calculateScore should change the score to 3 for the player two score");
    }

}
