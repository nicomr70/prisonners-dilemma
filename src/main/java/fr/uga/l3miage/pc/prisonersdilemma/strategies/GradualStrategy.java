package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

public class GradualStrategy implements Strategy {
    private int numberOfOpponentBetrays = 0;
    private int betrayCount = 0;
    private int cooperateCount = 2;
    @Override
    public Action play(Game game, PlayerNumber opponent) {
        if (isOpponentHistoryEmpty(game)) {
            return Action.COOPERATE;
        }

        if (betrayCount > 0) {
            betrayCount--;
            return Action.BETRAY;
        }

        if (cooperateCount > 0) {
            cooperateCount--;
            return Action.COOPERATE;         }

        if (hasOpponentBetrayed(game, opponent)) {
            numberOfOpponentBetrays++;
            betrayCount = numberOfOpponentBetrays;
            cooperateCount = 2;
            return Action.BETRAY;
        }

        return Action.COOPERATE;
    }

    private boolean isOpponentHistoryEmpty(Game game) {
        return game.getTurnThatJustEnded() == null;
    }

    private Action opponentLastAction(Game game, PlayerNumber opponent) {
        return game.getTurnThatJustEnded().getActionByPlayerNumber(opponent);
    }

    private boolean hasOpponentBetrayed(Game game, PlayerNumber opponent) {
        return opponentLastAction(game, opponent) == Action.BETRAY;
    }
}
