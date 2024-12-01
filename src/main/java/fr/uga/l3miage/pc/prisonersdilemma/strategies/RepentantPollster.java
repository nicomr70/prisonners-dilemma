package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RepentantPollster implements Strategy{

    private final Random random;


    public RepentantPollster(Random random) {
        this.random = random;

    }
    @Override
    public Action play(Game game, PlayerNumber opponent){

        if (getHistory(game, opponent).isEmpty()) {
            return Action.COOPERATE;
        }
        if (opponentHasBetrayedAfterRandomBetray(game , opponent)){
            return Action.COOPERATE;
        }
        if (isNextActionRandom() ){
            return Action.BETRAY;
        }
        return getPlayerLastAction(getHistory(game,opponent));
    }
    private boolean isNextActionRandom() {
        int randomInt = random.nextInt(2);
        return randomInt == 1;
    }

    private boolean opponentHasBetrayedAfterRandomBetray(Game game, PlayerNumber opponent){
        List<Action> opponentHistory = getHistory(game,opponent);
        List<Action> strategyHistory = getHistory(game, getStrategyPlayerNumber(opponent));
        return (getPlayerLastAction(opponentHistory)== getPlayerLastAction(strategyHistory))&& (getPlayerLastAction(strategyHistory) == Action.BETRAY);
    }

    private List<Action> getHistory(Game game, PlayerNumber opponent){
        return  game.getHistoryByPlayerNumber(opponent);
    }

    private Action getPlayerLastAction(List<Action> history){
        return history.get(history.size()-1);
    }

    public PlayerNumber getStrategyPlayerNumber(PlayerNumber opponent){
        if(opponent == PlayerNumber.PLAYER_ONE){
            return PlayerNumber.PLAYER_TWO;
        }
        return PlayerNumber.PLAYER_ONE;
    }

}
