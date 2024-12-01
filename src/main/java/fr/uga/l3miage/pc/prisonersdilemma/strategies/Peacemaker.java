package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.Turn;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Peacemaker implements Strategy{

    private final Random random;

    public Peacemaker(Random random) {
        this.random = random;
    }
    @Override
    public Action play(Game game, PlayerNumber opponent){
        if (isOpponentHistoryEmpty(game)) {
            return Action.COOPERATE;
        }
        if(hasOpponentBetrayed2TimesInARow(game,opponent)&& !isNextTurnARandomPeaceTurn()){
            return Action.BETRAY;
        }
        return Action.COOPERATE;
    }


    private boolean isNextTurnARandomPeaceTurn(){
        int randomInt = random.nextInt(2);
        return randomInt == 1;
    }

    private boolean isOpponentHistoryEmpty(Game game){
        return game.getTurnThatJustEnded() == null;
    }

    private List<Turn> last2opponentTurns(Game game){
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

    public boolean hasOpponentBetrayed2TimesInARow(Game game, PlayerNumber opponent){
        List<Turn> lastTwoTurns = last2opponentTurns(game);
        if(lastTwoTurns.isEmpty() || lastTwoTurns.size() == 1){
            return false;
        }else{
            return lastTwoTurns.get(0).getActionByPlayerNumber(opponent) == Action.BETRAY && lastTwoTurns.get(1).getActionByPlayerNumber(opponent) == Action.BETRAY;
        }

    }
}
