package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;

import java.util.List;

public class RandomStrategy implements Strategy{
    private final java.util.Random random;

    public RandomStrategy(java.util.Random random) {
        this.random = random;
    }
    @Override
    public Action play(List<Action> opponentHistory){
        int randomInt = random.nextInt(2);
        if (randomInt == 1){
            return Action.COOPERATE;
        }
        return Action.BETRAY;
    }
}
