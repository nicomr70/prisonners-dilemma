package fr.uga.l3miage.pc.prisonersdilemma.models;

import java.util.List;


public class AdaptativStrategy extends Strategy{
    @Override
    boolean play(List<Tour> history, int opponentPlayerNumber) {

        if(history.size() <5){
            return true;
        }
        if(history.size()<10){
            return false;
        }
        List<Tour> cooperateTurns =history.stream().filter(tour -> opponentPlayerNumber==1? tour.getPlayer2Decision():tour.getPlayer1Decision()).toList();
        double averageCooperateScore=cooperateTurns.stream().mapToDouble(tour -> tour.getPlayerScore(opponentPlayerNumber==1?2:1)).sum()/cooperateTurns.size();

        List<Tour> betrayTurns =history.stream().filter(tour -> opponentPlayerNumber==1? !tour.getPlayer2Decision(): !tour.getPlayer1Decision()).toList();
        double averageBetrayScore=betrayTurns.stream().mapToDouble(tour -> tour.getPlayerScore(opponentPlayerNumber==1?2:1)).sum()/betrayTurns.size();

        return averageCooperateScore>averageBetrayScore;
    }
}
