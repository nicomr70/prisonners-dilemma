package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.Random;

public class RepentantPollster implements Strategy{

    private final Random random;


    public RepentantPollster(Random random) {
        this.random = random;

    }
    @Override
    public Action play(Game game, PlayerNumber opponent){

        if (Utils.isOpponentHistoryEmpty(game)){
            return Action.COOPERATE;
        }
        if (opponentHasBetrayedAfterRandomBetray(game , opponent)){
            return Action.COOPERATE;
        }
        if (Utils.isNextPlayRandom(random) ){
            return Action.BETRAY;
        }
        return Utils.getOpponentLastAction(game,opponent);
    }
    private boolean opponentHasBetrayedAfterRandomBetray(Game game, PlayerNumber opponent){
        if(Utils.last2Turns(game).size() < 2){
            return false;
        }
        Action opponentReaction = Utils.last2Turns(game).get(1).getActionByPlayerNumber(opponent);
        Action strategyTurn = Utils.last2Turns(game).get(0).getActionByPlayerNumber(Utils.getStrategyPlayerNumber(opponent));
        return  opponentReaction == Action.BETRAY && strategyTurn == Action.BETRAY;
    }




}
