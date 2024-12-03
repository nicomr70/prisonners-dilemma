package fr.uga.l3miage.pc.prisonersdilemma.StrategiesTest;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.SoftResentful;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SoftResentfulTest {
    private SoftResentful strategy;
    private Game game;
//    private WebSocketSession mockSession;
    private PlayerNumber opponent;

    @BeforeEach
    public void setup() {
//        mockSession = mock(WebSocketSession.class);
        game = new Game(15, null);
        opponent = PlayerNumber.PLAYER_ONE;
        strategy = new SoftResentful();
    }

    @Test
    public void testInitialCooperation() {
        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "SoftResentful should cooperate on the first move.");
    }

    @Test
    public void testCooperationIfOpponentCooperates() {
        game.playTurn(Action.COOPERATE, opponent);
        game.playTurn(strategy.play(game, opponent), PlayerNumber.PLAYER_TWO);

        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "SoftResentful should cooperate if the opponent cooperated.");
    }

    @Test
    public void testPunishmentSequenceTriggered() {
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(strategy.play(game, opponent), PlayerNumber.PLAYER_TWO);

        for (int i = 0; i < 4; i++) {
            Action action = strategy.play(game, opponent);
            assertEquals(Action.BETRAY, action, "SoftResentful should betray during the punishment sequence.");
            game.playTurn(action, PlayerNumber.PLAYER_TWO);
        }

        for (int i = 0; i < 2; i++) {
            Action action = strategy.play(game, opponent);
            assertEquals(Action.COOPERATE, action, "SoftResentful should cooperate after 4 betrayals.");
            game.playTurn(action, PlayerNumber.PLAYER_TWO);
        }

        game.playTurn(Action.COOPERATE, opponent);
        Action action = strategy.play(game, opponent);
        assertEquals(Action.COOPERATE, action, "SoftResentful should return to cooperation after punishment sequence.");
    }

    @Test
    public void testMultiplePunishments() {
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(strategy.play(game, opponent), PlayerNumber.PLAYER_TWO);

        for (int i = 0; i < 6; i++) {
            game.playTurn(Action.COOPERATE, opponent);
            game.playTurn(strategy.play(game, opponent), PlayerNumber.PLAYER_TWO);
        }

        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(strategy.play(game, opponent), PlayerNumber.PLAYER_TWO);

        for (int i = 0; i < 4; i++) {
            game.playTurn(Action.BETRAY, opponent);
            Action action = strategy.play(game, opponent);
            assertEquals(Action.BETRAY, action, "SoftResentful should betray again during the second punishment sequence.");
            game.playTurn(action, PlayerNumber.PLAYER_TWO);
        }

        for (int i = 0; i < 2; i++) {
            game.playTurn(Action.BETRAY, opponent);
            Action action = strategy.play(game, opponent);
            assertEquals(Action.COOPERATE, action, "SoftResentful should cooperate after the second punishment sequence.");
            game.playTurn(action, PlayerNumber.PLAYER_TWO);
        }
    }

    @Test
    public void testNoPunishmentIfOpponentCooperates() {
        for (int i = 0; i < 5; i++) {
            game.playTurn(Action.COOPERATE, opponent);
            Action action = strategy.play(game, opponent);
            assertEquals(Action.COOPERATE, action, "SoftResentful should continue cooperating if opponent cooperates.");
            game.playTurn(action, PlayerNumber.PLAYER_TWO);
        }
    }

    @Test
    public void testPunishmentNotRestartedDuringSequence() {
        game.playTurn(Action.BETRAY, opponent);
        game.playTurn(strategy.play(game, opponent), PlayerNumber.PLAYER_TWO);

        for (int i = 0; i < 3; i++) {
            strategy.play(game, opponent);
        }

        game.playTurn(Action.BETRAY, opponent);
        Action action = strategy.play(game, opponent);

        assertEquals(Action.BETRAY, action, "SoftResentful should not restart punishment sequence during punishment.");
    }
}
