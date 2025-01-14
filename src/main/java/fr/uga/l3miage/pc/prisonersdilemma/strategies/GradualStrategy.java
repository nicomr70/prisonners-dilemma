package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

public class GradualStrategy extends Strategy {
    private int numberOfOpponentBetrays = 0;
    private int betrayCount = 0;
    private int cooperateCount = 2;
    @Override
    public Action play(Game game, PlayerNumber opponent) {
        if (Utils.isOpponentHistoryEmpty(game)) {
            return Action.COOPERATE;
        }

        if (betrayCount > 0) {
            betrayCount--;
            return Action.BETRAY;
        }

        if (cooperateCount > 0) {
            cooperateCount--;
            return Action.COOPERATE;         }

        if (Utils.hasOpponentBetrayed(game, opponent)) {
            numberOfOpponentBetrays++;
            betrayCount = numberOfOpponentBetrays;
            cooperateCount = 2;
            return Action.BETRAY;
        }

        return Action.COOPERATE;
    }

}
