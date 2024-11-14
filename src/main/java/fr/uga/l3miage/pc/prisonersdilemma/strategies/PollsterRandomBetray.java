package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;

import java.util.List;
import java.util.Random;

public class PollsterRandomBetray implements Strategy{

    private final Random random;

    public PollsterRandomBetray(Random random) {
        this.random = random;
    }

    @Override
    public Action play(List<Action> opponentHistory){
        if (opponentHistory.isEmpty()) {
            return Action.COOPERATE;
        }
        if (isNextActionBetray()){
            return Action.BETRAY;
        }
        return opponentHistory.get(opponentHistory.size()-1);
    }
    private boolean isNextActionBetray() {
        int randomInt = random.nextInt(2);
        return randomInt == 1;
    }

}
