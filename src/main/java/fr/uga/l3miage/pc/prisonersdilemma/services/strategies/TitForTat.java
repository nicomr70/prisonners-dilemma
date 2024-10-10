package fr.uga.l3miage.pc.prisonersdilemma.services.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.services.Strategy;
import fr.uga.l3miage.pc.prisonersdilemma.utils.Decision;

public class TitForTat implements Strategy {


    // Stratégie 1: Donnant donnant - Joue comme le dernier coup de l'adversaire
    @Override
    public Decision nextMove(Decision lastOpponentMove) {
        return lastOpponentMove;
    }

}
