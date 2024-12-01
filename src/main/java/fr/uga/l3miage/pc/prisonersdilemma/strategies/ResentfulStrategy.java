package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

public class ResentfulStrategy implements Strategy{

    private boolean hasBetrayed = false;
    @Override
    public Action play(Game game, PlayerNumber opponent) {
        if(isOpponentHistoryEmpty(game)){
            return Action.COOPERATE;
        }
        if(hasOpponentBetrayed(game, opponent)){
            return Action.BETRAY;
        }
        return Action.COOPERATE;
    }


    private boolean isOpponentHistoryEmpty(Game game){
        return game.getTurnThatJustEnded() == null;
    }

    private boolean hasOpponentBetrayed(Game game, PlayerNumber opponent){
        if (!hasBetrayed) {
            this.hasBetrayed = game.getTurnThatJustEnded().getActionByPlayerNumber(opponent) == Action.BETRAY;
        }
        return hasBetrayed;
    }
}
