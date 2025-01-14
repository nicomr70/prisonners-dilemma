package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

public class TitForTatSuspicious implements Strategy{
    @Override
    public Action play(Game game, PlayerNumber opponent){
        if (Utils.isOpponentHistoryEmpty(game)) {
            return Action.BETRAY;
        }
        return Utils.getOpponentLastAction(game, opponent);
    }

}
