package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.Turn;
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
        if (Utils.isOpponentHistoryEmpty(game)) {
            return Action.COOPERATE;
        }
        if(hasOpponentBetrayed2TimesInARow(game,opponent)&& !Utils.isNextPlayRandom(random)){
            return Action.BETRAY;
        }
        return Action.COOPERATE;
    }




    public boolean hasOpponentBetrayed2TimesInARow(Game game, PlayerNumber opponent){
        List<Turn> lastTwoTurns = Utils.last2Turns(game);
        if(lastTwoTurns.isEmpty() || lastTwoTurns.size() == 1){
            return false;
        }else{
            return lastTwoTurns.get(0).getActionByPlayerNumber(opponent) == Action.BETRAY && lastTwoTurns.get(1).getActionByPlayerNumber(opponent) == Action.BETRAY;
        }

    }
}
