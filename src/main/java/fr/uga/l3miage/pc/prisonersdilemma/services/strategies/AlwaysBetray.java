package fr.uga.l3miage.pc.prisonersdilemma.services.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.services.Strategy;
import fr.uga.l3miage.pc.prisonersdilemma.utils.Decision;

public class AlwaysBetray implements Strategy {

    // Stratégie 10: Toujours trahir
    @Override
    public Decision nextMove(Decision precMove) {
        return Decision.BETRAY;
    }
}
