package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.List;
import java.util.Random;

public class Peacemaker implements Strategy{

    private final Random random;

    public Peacemaker(Random random) {
        this.random = random;
    }
    @Override
    public Action play(Game game, PlayerNumber opponent){
        if (getOpponentHistory(game,opponent).size()<2) {
            return Action.COOPERATE;
        }
        if(hasOpponentBetrayed2TimesInARow(getOpponentHistory(game,opponent))&& !isNextTurnARandomPeaceTurn()){
            return Action.BETRAY;
        }
        return Action.COOPERATE;
    }


    private boolean hasOpponentBetrayed2TimesInARow(List<Action> opponentHistory){
        return (opponentHistory.get(opponentHistory.size()-1) == Action.BETRAY) && (opponentHistory.get(opponentHistory.size()-2) == Action.BETRAY);
    }

    private boolean isNextTurnARandomPeaceTurn(){
        int randomInt = random.nextInt(2);
        return randomInt == 1;
    }

    private List<Action> getOpponentHistory(Game game, PlayerNumber opponent){
        return  game.getHistoryByPlayerNumber(opponent);
    }

}
