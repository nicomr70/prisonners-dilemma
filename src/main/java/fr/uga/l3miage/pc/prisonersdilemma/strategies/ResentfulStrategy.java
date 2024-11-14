package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;

import java.util.List;

public class ResentfulStrategy implements Strategy{


    @Override
    public Action play(List<Action> opponentHistory) {
        if(opponentHistory.isEmpty()){
            return Action.COOPERATE;
        }
        if(opponentHasBetrayed(opponentHistory)){
            return Action.BETRAY;
        }
        return Action.COOPERATE;
    }

    private boolean opponentHasBetrayed(List<Action> opponentHistory){
        return opponentHistory.get(opponentHistory.size()-1) == Action.BETRAY;
    }
}
