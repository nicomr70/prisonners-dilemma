package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.Turn;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.List;
import java.util.Random;

public class TitforTwoTatsRandom extends Strategy {
    private final Random random;
    private boolean startReciprocity = false;

    public TitforTwoTatsRandom(Random random) {
        this.random = random;
    }

    @Override
    public Action play(Game game, PlayerNumber opponent) {
        if (Utils.isOpponentHistoryEmpty(game)) {
            return Action.COOPERATE;
        }

        boolean nextPlayIsRandom = Utils.isNextPlayRandom(random);
        if (nextPlayIsRandom) {
            return Utils.playNextTurnRandom(random);
        }

        if (startReciprocity) {
            return Utils.getOpponentLastAction(game, opponent);
        }

        if (isBothLastOpponentActionsSame(game, opponent)) {
            startReciprocity = true;
            return Utils.getOpponentLastAction(game, opponent);
        }

        return Action.BETRAY;
    }



    private boolean isBothLastOpponentActionsSame(Game game, PlayerNumber opponent) {
        List<Turn> last2Turns = Utils.last2Turns(game);
        if (last2Turns.size() < 2) {
            return false;
        }
        return last2Turns.get(0).getActionByPlayerNumber(opponent) ==
                last2Turns.get(1).getActionByPlayerNumber(opponent);
    }
}
