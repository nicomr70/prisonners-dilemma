package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

public class TitforTwoTats implements Strategy{


    private boolean startReciprocity = false ;


    @Override
    public Action play(Game game, PlayerNumber opponent){
        if (Utils.isOpponentHistoryEmpty(game)) {
            return Action.COOPERATE;
        }
        if(startReciprocity){
            return Utils.getOpponentLastAction(game, opponent);
        }
        if (isBothLastOpponentActionsSame(game,opponent)){
            startReciprocity = true;
            return Utils.getOpponentLastAction(game, opponent);
        }
        return Action.BETRAY;
    }


    private boolean isBothLastOpponentActionsSame(Game game, PlayerNumber opponent){
        if(Utils.last2Turns(game).size() < 2){
            return false;
        }
        return Utils.last2Turns(game).get(0).getActionByPlayerNumber(opponent) == Utils.last2Turns(game).get(1).getActionByPlayerNumber(opponent);
    }


}
