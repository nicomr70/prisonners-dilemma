package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.Turn;
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

        if (isOpponentHistoryEmpty(game)){
            return Action.COOPERATE;
        }
        if (opponentHasBetrayedAfterRandomBetray(game , opponent)){
            return Action.COOPERATE;
        }
        if (isNextActionRandom() ){
            return Action.BETRAY;
        }
        return getPlayerLastAction(game,opponent);
    }
    private boolean isNextActionRandom() {
        int randomInt = random.nextInt(2);
        return randomInt == 1;
    }

    private boolean opponentHasBetrayedAfterRandomBetray(Game game, PlayerNumber opponent){
        if(last2Turns(game).size() < 2){
            return false;
        }
        Action opponentReaction = last2Turns(game).get(1).getActionByPlayerNumber(opponent);
        Action strategyTurn = last2Turns(game).get(0).getActionByPlayerNumber(getStrategyPlayerNumber(opponent));
        return  opponentReaction == Action.BETRAY && strategyTurn == Action.BETRAY;
    }

    private List<Turn> last2Turns(Game game){
        List<Turn> lastTwoTurns = new ArrayList<>();
        int currentTurn = game.getCurrentTurn();
        if (currentTurn == 0) {
            return lastTwoTurns;
        }
        int start = Math.max(0, currentTurn - 2);
        for (int i = start; i < currentTurn; i++) {
            lastTwoTurns.add(game.getTurns()[i]);
        }

        return lastTwoTurns;
    }

    private Action getPlayerLastAction(Game game, PlayerNumber opponent){
        return game.getTurnThatJustEnded().getActionByPlayerNumber(opponent);
    }

    public PlayerNumber getStrategyPlayerNumber(PlayerNumber opponent){
        if(opponent == PlayerNumber.PLAYER_ONE){
            return PlayerNumber.PLAYER_TWO;
        }
        return PlayerNumber.PLAYER_ONE;
    }

    private boolean isOpponentHistoryEmpty(Game game){
        return game.getTurnThatJustEnded() == null;
    }

}
