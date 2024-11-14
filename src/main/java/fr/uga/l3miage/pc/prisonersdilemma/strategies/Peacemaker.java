package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;

import java.util.List;
import java.util.Random;

public class Peacemaker implements Strategy{

    private final Random random;

    public Peacemaker(Random random) {
        this.random = random;
    }
    @Override
    public Action play(List<Action> opponentHistory){
        if (opponentHistory.size()<2) {
            return Action.COOPERATE;
        }
        if(hasOpponentBetrayed2TimesInARow(opponentHistory)&& !isNextTurnARandomPeaceTurn()){
            return Action.BETRAY;
        }
        return Action.COOPERATE;
    }


    private boolean hasOpponentBetrayed2TimesInARow(List<Action> opponentHistory){
        return (opponentHistory.get(opponentHistory.size()-1) == Action.BETRAY) && (opponentHistory.get(opponentHistory.size()-2) == Action.BETRAY);
    }

    private boolean isNextTurnARandomPeaceTurn(){
        int randomInt = random.nextInt(2);
        return randomInt == 1;
    }

}
