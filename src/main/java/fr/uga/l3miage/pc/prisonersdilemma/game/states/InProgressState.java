package fr.uga.l3miage.pc.prisonersdilemma.game.states;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

public class InProgressState extends  State {


    public InProgressState(Game game) {
        super(game);
    }

    @Override
    public void getNextState() {
        game.changeState(new CompletedState(game));
    }

    @Override
    public void play( Action action, PlayerNumber playerNumber ) {

        if(game.getCurrentTurn() >= game.getMaxTurns() - 1 && game.atLeastOnePlayerHasPlayedHisTurn()){
            game.playTurn( action, playerNumber );
            game.changeState(new CompletedState(game));
        }else {
            game.playTurn( action, playerNumber );
        }

    }

}
