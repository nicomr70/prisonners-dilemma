package fr.uga.l3miage.pc.prisonersdilemma.models;

import java.util.List;

public class DonnantDonnantSuspiciousStrategy extends Strategy{
    @Override
     boolean play(List<Tour> history,int opponentPlayerNumber) {
        if(history.isEmpty()){
            return false;
        }
        Tour lastTour=history.getLast();
        return opponentPlayerNumber==1 ? lastTour.getPlayer1Decision():lastTour.getPlayer2Decision() ;
    }
}
