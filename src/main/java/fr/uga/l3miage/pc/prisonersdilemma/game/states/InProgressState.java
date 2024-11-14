package fr.uga.l3miage.pc.prisonersdilemma.game.states;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

public class InProgressState extends  State {


    public InProgressState(Game game) {
        super(game);
    }

    @Override
    public void play( Action action, PlayerNumber playerNumber ) {

        if(game.getTurns().length > game.getMaxTurns() - 1 ){
            game.changeState(new CompletedState(game));
        }else {
            game.playTurn( action, playerNumber );
        }

    }

}
