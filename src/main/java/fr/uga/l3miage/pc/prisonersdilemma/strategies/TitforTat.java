package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;

import java.util.List;

public class TitforTat implements Strategy{

    @Override
    public Action play(List<Action> opponentHistory){
        if (opponentHistory.isEmpty()) {
            return Action.COOPERATE;
        }
        return opponentHistory.get(opponentHistory.size()-1);
    }
}
