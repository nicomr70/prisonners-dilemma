package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.List;
import java.util.Random;

public class NaivePeacemaker implements Strategy{
    private final Random random;

    public NaivePeacemaker(Random random) {
        this.random = random;
    }
    @Override
    public Action play(Game game, PlayerNumber opponent){
        if (isOpponentHistoryEmpty(game, opponent)) {
            return Action.COOPERATE;
        }
        if(hasOpponentBetrayed(game, opponent) && isNextActionCooperate()) {
                return Action.COOPERATE;
        }
        return opponentLastAction(game, opponent);

    }

    private boolean hasOpponentBetrayed(Game game, PlayerNumber opponent){
        return getOpponentHistory(game, opponent).get(getOpponentHistory(game, opponent).size()-1) == Action.BETRAY;
    }

    private boolean isNextActionCooperate() {
        int randomInt = random.nextInt(2);
        return randomInt == 1;
    }

    private List<Action> getOpponentHistory(Game game, PlayerNumber opponent){
        return  game.getHistoryByPlayerNumber(opponent);
    }

    private Action opponentLastAction(Game game, PlayerNumber opponent){
        return getOpponentHistory(game, opponent).get(getOpponentHistory(game, opponent).size()-1);
    }

    private boolean isOpponentHistoryEmpty(Game game, PlayerNumber opponent){
        List<Action> opponentHistory = getOpponentHistory(game, opponent);
        int i = 0;
        while(i < opponentHistory.size()-1 && opponentHistory.get(i) == Action.NONE){
            i++;
        }
        return i == opponentHistory.size()-1;
    }
}
