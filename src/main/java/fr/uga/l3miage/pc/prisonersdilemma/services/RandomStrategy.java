package fr.uga.l3miage.pc.prisonersdilemma.services;

import fr.uga.l3miage.pc.prisonersdilemma.utils.Decision;

import java.util.Random;

public class RandomStrategy implements Strategy {
    private Random random = new Random();

    @Override
    public Decision nextMove() {
        return random.nextBoolean() ? Decision.COOPERATE : Decision.BETRAY;
    }
}
