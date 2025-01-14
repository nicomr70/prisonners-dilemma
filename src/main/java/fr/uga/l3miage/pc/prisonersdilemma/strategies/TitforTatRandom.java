package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.Random;

public class TitforTatRandom extends Strategy{

    private final Random random;

    public TitforTatRandom(Random random) {
        this.random = random;
    }
    @Override
    public Action play(Game game, PlayerNumber opponent){
        if (Utils.isOpponentHistoryEmpty(game)) {
            return Action.COOPERATE;
        }
        if (Utils.isNextPlayRandom(random)) {
            return Utils.playNextTurnRandom(random);
        }
        return Utils.getOpponentLastAction(game, opponent);
    }



}
