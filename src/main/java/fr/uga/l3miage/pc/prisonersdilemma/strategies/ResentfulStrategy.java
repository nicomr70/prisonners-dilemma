package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.List;

public class ResentfulStrategy implements Strategy{


    @Override
    public Action play(Game game, PlayerNumber opponent) {
        if(getHistory(game,opponent).isEmpty()){
            return Action.COOPERATE;
        }
        if(opponentHasBetrayed(game, opponent)){
            return Action.BETRAY;
        }
        return Action.COOPERATE;
    }

    private boolean opponentHasBetrayed(Game game, PlayerNumber opponent){
        return getOpponentLastAction(getHistory(game,opponent)) == Action.BETRAY;
    }

    private List<Action> getHistory(Game game, PlayerNumber opponent){
        return  game.getHistoryByPlayerNumber(opponent);
    }

    private Action getOpponentLastAction(List<Action> history){
        return history.get(history.size()-1);
    }


}
