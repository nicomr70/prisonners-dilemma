package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.Random;

public class PavlovRandom extends Strategy{

    private final Random random;

    public PavlovRandom(Random random) {
        this.random = random;
    }


    @Override
    public Action play(Game game, PlayerNumber opponent) {
        if(Utils.isOpponentHistoryEmpty(game)){
            return Action.COOPERATE;
        }
        if(Utils.isLastScoreSufficient(game, opponent)){
            if(Utils.isNextPlayRandom(random)){
                return Utils.playNextTurnRandom(random);
            }
            return Utils.getStrategyLastAction(game, opponent);
        }
        return Action.COOPERATE;
    }


}
