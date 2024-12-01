package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.List;
import java.util.Random;

public class TitforTwoTats implements Strategy{


    private boolean startReciprocity = false ;


    @Override
    public Action play(Game game, PlayerNumber opponent){
        if (getHistory(game, opponent).size() < 2) {
            return Action.COOPERATE;
        }
        if(startReciprocity){
            return getOpponentLastAction(getHistory(game,opponent));
        }
        if (isBothLastOpponentActionsSame(getHistory(game,opponent))){
            startReciprocity = true;
            return getOpponentLastAction(getHistory(game,opponent));
        }
        return Action.BETRAY;
    }


    private boolean isBothLastOpponentActionsSame(List<Action> opponentHistory){
        return getOpponentLastAction(opponentHistory) == getOpponentPreviousLastAction(opponentHistory);
    }


    private List<Action> getHistory(Game game, PlayerNumber opponent){
        return  game.getHistoryByPlayerNumber(opponent);
    }

    private Action getOpponentLastAction(List<Action> history){
        return history.get(history.size()-1);
    }
    private Action getOpponentPreviousLastAction(List<Action> history){
        return history.get(history.size()-2);
    }
}
