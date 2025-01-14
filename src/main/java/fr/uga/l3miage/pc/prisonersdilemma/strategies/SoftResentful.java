package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

public class SoftResentful extends Strategy {
    private int resentfulRoundCounter = 0;

    @Override
    public Action play(Game game, PlayerNumber opponent) {
        if (Utils.isOpponentHistoryEmpty(game)) {
            return Action.COOPERATE;
        }

        if (isResentfulActive()) {
            return handleResentfulRounds();
        }

        if (Utils.hasOpponentBetrayed(game, opponent)) {
            activateResentfulRounds();
            return handleResentfulRounds();
        }

        return Action.COOPERATE;
    }



    private boolean isResentfulActive() {
        return resentfulRoundCounter > 0;
    }

    private void activateResentfulRounds() {
        resentfulRoundCounter = 6;
    }

    private Action handleResentfulRounds() {
        if (resentfulRoundCounter > 2) {
            resentfulRoundCounter--;
            return Action.BETRAY;
        } else {
            resentfulRoundCounter--;
            return Action.COOPERATE;
        }
    }
}
