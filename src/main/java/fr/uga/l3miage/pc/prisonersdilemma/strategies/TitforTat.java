package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.List;

public class TitforTat implements Strategy{

    @Override
    public Action play(Game game, PlayerNumber opponent){
        if (getHistory(game,opponent).isEmpty()) {
            return Action.COOPERATE;
        }
        return getOpponentLastAction(getHistory(game,opponent));
    }

    private List<Action> getHistory(Game game, PlayerNumber opponent){
        return  game.getHistoryByPlayerNumber(opponent);
    }

    private Action getOpponentLastAction(List<Action> history){
        return history.get(history.size()-1);
    }
}
