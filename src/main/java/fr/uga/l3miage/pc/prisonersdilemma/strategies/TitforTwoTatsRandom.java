package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.List;
import java.util.Random;

public class TitforTwoTatsRandom implements Strategy{
    private final Random random;
    private boolean startReciprocity = false;

    public TitforTwoTatsRandom(Random random) {
        this.random = random;
    }
    @Override
    public Action play(Game game, PlayerNumber opponent){
        if (getHistory(game, opponent).size() < 2) {
            return Action.COOPERATE;
        }
        if(startReciprocity && !isNextPlayRandom()){
            return getOpponentLastAction(getHistory(game,opponent));
        }
        if (isBothLastOpponentActionsSame(getHistory(game,opponent))){
            startReciprocity = true;
            return getOpponentLastAction(getHistory(game,opponent));
        }
        if(isNextPlayRandom()){
            return playNextTurnRandom();
        }
        return Action.BETRAY;
    }


    private Action playNextTurnRandom(){

        int randomInt = random.nextInt(2);
        if (randomInt == 1){
            return Action.COOPERATE;
        }
        return Action.BETRAY;
    }

    private boolean isBothLastOpponentActionsSame(List<Action> opponentHistory){
        return getOpponentLastAction(opponentHistory) == getOpponentPreviousLastAction(opponentHistory);
    }

    private List<Action> getHistory(Game game, PlayerNumber opponent){
        return  game.getHistoryByPlayerNumber(opponent);
    }

    private Action getOpponentLastAction(List<Action> history) {
        return history.get(history.size() - 1);
    }

    private Action getOpponentPreviousLastAction(List<Action> history){
        return history.get(history.size()-2);
    }

    private boolean isNextPlayRandom(){
        int randomInt = random.nextInt(2);
        return randomInt == 1;
    }
}
