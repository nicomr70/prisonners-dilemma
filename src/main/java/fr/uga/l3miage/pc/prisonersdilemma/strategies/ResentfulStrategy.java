package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

public class ResentfulStrategy extends Strategy{

    private boolean hasBetrayed = false;
    @Override
    public Action play(Game game, PlayerNumber opponent) {
        if(Utils.isOpponentHistoryEmpty(game)){
            return Action.COOPERATE;
        }
        if(hasOpponentBetrayed(game, opponent)){
            return Action.BETRAY;
        }
        return Action.COOPERATE;
    }

    private boolean hasOpponentBetrayed(Game game, PlayerNumber opponent){
        if (!hasBetrayed) {
            this.hasBetrayed = Utils.hasOpponentBetrayed(game, opponent);
        }
        return hasBetrayed;
    }
}
