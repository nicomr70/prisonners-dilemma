package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;

import java.util.List;
import java.util.Random;

public class NaivePeacemaker implements Strategy{
    private final Random random;

    public NaivePeacemaker(Random random) {
        this.random = random;
    }
    @Override
    public Action play(List<Action> opponentHistory){
        if (opponentHistory.isEmpty()) {
            return Action.COOPERATE;
        }
        if(hasOpponentBetrayed(opponentHistory ) && isNextActionCooperate()) {
                return Action.COOPERATE;
        }
        return opponentHistory.get(opponentHistory.size() - 1);

    }

    private boolean hasOpponentBetrayed(List<Action> opponentHistory){
        return opponentHistory.get(opponentHistory.size()-1) == Action.BETRAY;
    }

    private boolean isNextActionCooperate() {
        int randomInt = random.nextInt(2);
        return randomInt == 1;
    }
}
