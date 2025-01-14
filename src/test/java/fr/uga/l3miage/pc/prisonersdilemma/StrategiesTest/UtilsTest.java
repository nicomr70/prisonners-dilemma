package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.Turn;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UtilsTest {

    private Game game;
    private PlayerNumber opponent;
    private Random mockRandom;
    private WebSocketSession mockSession;

    @BeforeEach
    void setup() {
        mockSession = mock(WebSocketSession.class);
        game = new Game(5, mockSession);
        opponent = PlayerNumber.PLAYER_ONE;
        mockRandom = Mockito.mock(Random.class);
    }


    @Test
    void isOpponentHistoryEmptyTrue(){
        Boolean result = Utils.isOpponentHistoryEmpty(game);
        assertEquals(true, result, "isOpponentHistory should return true if the game's history is empty");
    }

    @Test
    void isOpponentHistoryEmptyFalse(){
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
        game.playTurn(Action.COOPERATE, opponent);

        Boolean result = Utils.isOpponentHistoryEmpty(game);
        assertEquals(false, result, "isOpponentHistory should return false if the game's history is not empty");
    }

    @Test
    void getOpponentLastActionOneRound() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
        game.playTurn(Action.BETRAY, opponent);

        Action result = Utils.getOpponentLastAction(game,opponent);
        assertEquals(Action.COOPERATE, result, "getOpponentLastAction action should return COOPERATE");
    }

    @Test
    void getOpponentLastActionMultipleRounds() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);

        Action result = Utils.getOpponentLastAction(game,opponent);
        assertEquals(Action.BETRAY, result, "getOpponentLastAction action should return COOPERATE");
    }

    @Test
    void getLastTwoTurnsTest(){
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);

        List<Turn> result = Utils.last2Turns(game);
        assertEquals(2, result.size());
        assertEquals(Action.COOPERATE, result.get(0).getActionByPlayerNumber(opponent));
        assertEquals(Action.BETRAY, result.get(1).getActionByPlayerNumber(PlayerNumber.PLAYER_TWO));
    }

    @Test
    void hasOpponentBetrayedFalseOneRound(){
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);

        Boolean result = Utils.hasOpponentBetrayed(game, opponent);
        assertEquals(false, result);
    }

    @Test
    void hasOpponentBetrayedTrueOneRound(){
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);

        Boolean result = Utils.hasOpponentBetrayed(game, opponent);
        assertEquals(true, result);
    }

    @Test
    void hasOpponentBetrayedMultipleRounds(){
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);

        Boolean result1 = Utils.hasOpponentBetrayed(game, opponent);

        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);

        Boolean result2 = Utils.hasOpponentBetrayed(game, opponent);

        assertEquals(false, result1);
        assertEquals(true, result2);
    }

    @Test
    void isNextPlayRandomTrue(){
        when(mockRandom.nextInt(2)).thenReturn(1);
        boolean result = Utils.isNextPlayRandom(mockRandom);
        assertTrue(result);
    }

    @Test
    void isNextPlayRandomFalse(){
        when(mockRandom.nextInt(2)).thenReturn(0);
        boolean result = Utils.isNextPlayRandom(mockRandom);
        assertFalse(result);
    }

    @Test
    void testPlayNextTurnRandomCooperate() {
        when(mockRandom.nextInt(2)).thenReturn(1);
        Action result = Utils.playNextTurnRandom(mockRandom);
        assertEquals(Action.COOPERATE, result);
    }

    @Test
    void testPlayNextTurnRandomBetray() {
        when(mockRandom.nextInt(2)).thenReturn(0);
        Action result = Utils.playNextTurnRandom(mockRandom);
        assertEquals(Action.BETRAY, result);
    }

    @Test
    void testGetStrategyPlayerNumberPlayerOne(){
        PlayerNumber result = Utils.getStrategyPlayerNumber(opponent);
        assertEquals(PlayerNumber.PLAYER_TWO, result);
    }

    @Test
    void testGetStrategyPlayerNumberPlayerTwo() {
        PlayerNumber result = Utils.getStrategyPlayerNumber(PlayerNumber.PLAYER_TWO);
        assertEquals(PlayerNumber.PLAYER_ONE, result);
    }

    @Test
    void testIsLastScoreSufficientTrue(){
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
        boolean result = Utils.isLastScoreSufficient(game,opponent);

        assertTrue(result);
    }

    @Test
    void testIsLastScoreSufficientTrue2(){
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
        boolean result = Utils.isLastScoreSufficient(game,opponent);

        assertTrue(result);
    }

    @Test
    void testIsLastScoreSufficientFalse(){
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
        boolean result = Utils.isLastScoreSufficient(game,opponent);

        assertFalse(result);
    }

    @Test
    void testIsLastScoreSufficientFalse2(){
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
        boolean result = Utils.isLastScoreSufficient(game,opponent);

        assertFalse(result);
    }

    @Test
    void testGetStrategyLastActionCooperate(){
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(Action.COOPERATE, PlayerNumber.PLAYER_TWO);
        Action result = Utils.getStrategyLastAction(game, opponent);

        assertEquals(Action.COOPERATE, result);
    }

    @Test
    void testGetStrategyLastActionBetray(){
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(Action.BETRAY, PlayerNumber.PLAYER_TWO);
        Action result = Utils.getStrategyLastAction(game, opponent);

        assertEquals(Action.BETRAY, result);
    }
}
