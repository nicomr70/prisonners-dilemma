package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;

import java.util.List;
import java.util.Random;

public class TitforTatRandom implements Strategy{

    private final Random random;

    public TitforTatRandom(Random random) {
        this.random = random;
    }
    @Override
    public Action play(List<Action> opponentHistory){
        if (opponentHistory.isEmpty()) {
            return Action.COOPERATE;
        }
        if (isNextActionRandom()) {
            return playNextTurnRandom();
        }

        return opponentHistory.get(opponentHistory.size()-1);
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
}
