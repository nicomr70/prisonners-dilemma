package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.Random;

public class NaivePeacemaker implements Strategy{
    private final Random random;

    public NaivePeacemaker(Random random) {
        this.random = random;
    }
    @Override
    public Action play(Game game, PlayerNumber opponent){
        if (isOpponentHistoryEmpty(game)) {
            return Action.COOPERATE;
        }
        if(hasOpponentBetrayed(game, opponent) && isNextActionCooperate()) {
            return Action.COOPERATE;
        }
        return opponentLastAction(game, opponent);

    }

    private boolean hasOpponentBetrayed(Game game, PlayerNumber opponent){
        return opponentLastAction(game, opponent) == Action.BETRAY;
    }

    private boolean isNextActionCooperate() {
        int randomInt = random.nextInt(2);
        return randomInt == 1;
    }

    private Action opponentLastAction(Game game, PlayerNumber opponent){
        return game.getTurnThatJustEnded().getActionByPlayerNumber(opponent);
    }

    private boolean isOpponentHistoryEmpty(Game game){
        return game.getTurnThatJustEnded() == null;
    }
}
