package fr.uga.l3miage.pc.prisonersdilemma.services.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.services.Strategy;
import fr.uga.l3miage.pc.prisonersdilemma.utils.Decision;

import java.util.Random;

public class TitForTatRandom implements Strategy {

    private Random random = new Random();

    @Override
    public Decision nextMove(Decision lastOpponentMove) {
        return random.nextDouble() < 0.1 ? random.nextBoolean() ? Decision.COOPERATE : Decision.BETRAY : lastOpponentMove;
    }
}
