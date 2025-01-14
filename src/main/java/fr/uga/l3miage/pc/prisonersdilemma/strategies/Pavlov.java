package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

public class Pavlov implements Strategy{
    @Override
    public Action play(Game game, PlayerNumber opponent) {
        if(Utils.isOpponentHistoryEmpty(game)){
            return Action.COOPERATE;
        }
        if(Utils.isLastScoreSufficient(game, opponent)){
            return Utils.getStrategyLastAction(game, opponent);
        }
        return Action.COOPERATE;
    }


}
