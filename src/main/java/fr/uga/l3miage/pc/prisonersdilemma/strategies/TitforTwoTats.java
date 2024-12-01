package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.Turn;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.ArrayList;
import java.util.List;

public class TitforTwoTats implements Strategy{


    private boolean startReciprocity = false ;


    @Override
    public Action play(Game game, PlayerNumber opponent){
        if (isOpponentHistoryEmpty(game)) {
            return Action.COOPERATE;
        }
        if(startReciprocity){
            return getOpponentLastAction(game, opponent);
        }
        if (isBothLastOpponentActionsSame(game,opponent)){
            startReciprocity = true;
            return getOpponentLastAction(game, opponent);
        }
        return Action.BETRAY;
    }


    private boolean isBothLastOpponentActionsSame(Game game, PlayerNumber opponent){
        if(last2Turns(game).size() < 2){
            return false;
        }
        return last2Turns(game).get(0).getActionByPlayerNumber(opponent) == last2Turns(game).get(1).getActionByPlayerNumber(opponent);
    }

    private Action getOpponentLastAction(Game game, PlayerNumber opponent) {
        return game.getTurnThatJustEnded().getActionByPlayerNumber(opponent);
    }

    private boolean isOpponentHistoryEmpty(Game game){
        return game.getTurnThatJustEnded() == null;
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
}
