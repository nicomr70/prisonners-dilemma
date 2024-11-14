package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;

import java.util.List;
import java.util.Random;

public class TitforTwoTatsRandom implements Strategy{
    private final Random random;

    public TitforTwoTatsRandom(Random random) {
        this.random = random;
    }
    @Override
    public Action play(List<Action> opponentHistory){
        if (opponentHistory.size() < 2) {
            return Action.COOPERATE;
        }
        if (isBothLastOpponentActionsSame(opponentHistory)){
            return opponentHistory.get(opponentHistory.size()-1);
        }
        return playNextTurnRandom();
    }


    private Action playNextTurnRandom(){

        int randomInt = random.nextInt(2);
        if (randomInt == 1){
            return Action.COOPERATE;
        }
        return Action.BETRAY;
    }

    private boolean isBothLastOpponentActionsSame(List<Action> opponentHistory){
        return opponentHistory.get(opponentHistory.size()-1) == opponentHistory.get(opponentHistory.size()-2);
    }
}
