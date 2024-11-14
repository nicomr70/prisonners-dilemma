package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;

import java.util.List;

public class Turn {
    private Action[] turn;
    public Turn(Action actionPlayerOne, Action actionPlayerTwo){
        this.turn =  new Action[2];
        turn[0] = actionPlayerOne;
        turn[1] = actionPlayerTwo;

    }

    public void updateTurn(Action actionPlayer, PlayerNumber playerNumber){
        this.turn[playerNumber.getIndex()] = actionPlayer;
    }

    public Action getActionByPlayerNumber(PlayerNumber playerNumber){
        return this.turn[playerNumber.getIndex()];
    }

    public Action getPlayerOneAction() {
        return this.turn[0];
    }

    public Action getPlayerTwoAction() {
        return this.turn[1];
    }
}
