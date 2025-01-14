package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;



public class RandomStrategy implements Strategy{
    private final java.util.Random random;

    public RandomStrategy(java.util.Random random) {
        this.random = random;
    }
    @Override
    public Action play(Game game, PlayerNumber playerNumber){
        return Utils.playNextTurnRandom(random);
    }
}
