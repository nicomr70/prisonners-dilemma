package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.Turn;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {



    public static boolean isOpponentHistoryEmpty(Game game){
        return game.getTurnThatJustEnded() == null;
    }

    public static Action getOpponentLastAction(Game game, PlayerNumber opponent) {
        return game.getTurnThatJustEnded().getActionByPlayerNumber(opponent);
    }

    public static List<Turn> last2Turns(Game game){
        List<Turn> lastTwoTurns = new ArrayList<>();
        int currentTurn = game.getCurrentTurn();
        if (currentTurn == 0) {
            return lastTwoTurns;
        }
        int start = Math.max(0, currentTurn - 2);
        lastTwoTurns.addAll(Arrays.asList(game.getTurns()).subList(start, currentTurn));

        return lastTwoTurns;
    }

    public static boolean hasOpponentBetrayed(Game game, PlayerNumber opponent) {
        return getOpponentLastAction(game, opponent) == Action.BETRAY;
    }

    public static boolean isNextPlayRandom(Random random){
        int randomInt = random.nextInt(2);
        return randomInt == 1;
    }

    public static Action playNextTurnRandom(Random random) {
        return isNextPlayRandom(random) ? Action.COOPERATE : Action.BETRAY;
    }

    public static PlayerNumber getStrategyPlayerNumber(PlayerNumber opponent){
        if(opponent == PlayerNumber.PLAYER_ONE){
            return PlayerNumber.PLAYER_TWO;
        }
        return PlayerNumber.PLAYER_ONE;
    }

    public static boolean isLastScoreSufficient(Game game, PlayerNumber opponent){
        PlayerNumber strategyPlayerNumber = getStrategyPlayerNumber(opponent);
        return game.getScoreByTurnNumberAndByPlayerNumber(game.getCurrentTurn()-1,strategyPlayerNumber) >= 3;
    }

    public static Action getStrategyLastAction(Game game, PlayerNumber opponent){
        return game.getTurnThatJustEnded().getActionByPlayerNumber(getStrategyPlayerNumber(opponent));
    }
}
