package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.List;
import java.util.Random;

public class TitforTatRandom implements Strategy{

    private final Random random;

    public TitforTatRandom(Random random) {
        this.random = random;
    }
    @Override
    public Action play(Game game, PlayerNumber opponent){
        if (getHistory(game,opponent).isEmpty()) {
            return Action.COOPERATE;
        }
        if (isNextActionRandom()) {
            return playNextTurnRandom();
        }

        return getOpponentLastAction(getHistory(game, opponent));
    }

    private boolean isNextActionRandom() {
        int randomInt = random.nextInt(2);
        return randomInt == 1;
    }

    private Action playNextTurnRandom(){
        int randomInt = random.nextInt(2);
        if (randomInt == 1){
            return Action.COOPERATE;
        }
        return Action.BETRAY;
    }
    private List<Action> getHistory(Game game, PlayerNumber opponent){
        return  game.getHistoryByPlayerNumber(opponent);
    }

    private Action getOpponentLastAction(List<Action> history){
        return history.get(history.size()-1);
    }
}
