package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;

import java.util.List;

public class AlwaysBetray implements Strategy{
    @Override
    public Action play(List<Action> opponentHistory) {
        return Action.BETRAY;
    }


}
