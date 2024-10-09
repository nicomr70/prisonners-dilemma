package fr.uga.l3miage.pc.prisonersdilemma.services;

import fr.uga.l3miage.pc.prisonersdilemma.entities.Player;
import fr.uga.l3miage.pc.prisonersdilemma.utils.Decision;

import java.util.UUID;

public class GameService {

    private Player thePlayer1, thePlayer2;


    public GameService(Player player1, Player player2) {
        thePlayer1 = player1;
        thePlayer2 = player2;
    }

    public boolean secondPlayerHaveJoinTheGame() {
        if (this.thePlayer2 == null) {
            System.out.println("We need a second player, of course! Come on, try again!");
            return false;
        }
        return true;
    }

    public static void decisionIsValid(String decision) throws Exception {
        if (!decision.equals("COOPERATE") && !decision.equals("BETRAY")) {
            throw new Exception("Invalid decision");
        }
    }

    public void userExistAndActiveInGame (UUID playerId, Player thePlayer1, Player thePlayer2) throws Exception {
        if (!this.verifyPlayer(playerId, thePlayer1) && !this.verifyPlayer(playerId, thePlayer2))
            throw new Exception("The specified player hasn't been found");
    }

    public boolean verifyPlayer(UUID playerId, Player player) {
        return playerId == player.getPlayerId() && player.getConnected();
    }

}
