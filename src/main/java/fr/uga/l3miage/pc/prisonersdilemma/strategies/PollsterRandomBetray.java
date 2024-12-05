package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.Random;

public class PollsterRandomBetray implements Strategy{

    private final Random random;

    public PollsterRandomBetray(Random random) {
        this.random = random;
    }

    @Override
    public Action play(Game game, PlayerNumber opponent){
        if (isOpponentHistoryEmpty(game)) {
            return Action.COOPERATE;
        }
        if (isNextActionBetray()){
            return Action.BETRAY;
        }
        return opponentLastAction(game,opponent);
    }
    private boolean isNextActionBetray() {
        int randomInt = random.nextInt(2);
        return randomInt == 1;
    }

    private boolean isOpponentHistoryEmpty(Game game){
        return game.getTurnThatJustEnded() == null;
    }
    private Action opponentLastAction(Game game, PlayerNumber opponent){
        return game.getTurnThatJustEnded().getActionByPlayerNumber(opponent);
    }

}
