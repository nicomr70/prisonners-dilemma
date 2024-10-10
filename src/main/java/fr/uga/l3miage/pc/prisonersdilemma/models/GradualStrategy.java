package fr.uga.l3miage.pc.prisonersdilemma.models;

import java.util.List;
import java.util.Stack;

public class GradualStrategy extends  Strategy{
    private final Stack<Boolean> playLeft=new Stack<>();
    @Override
    boolean play(List<Tour> history, int opponentPlayerNumber) {
        if(!playLeft.isEmpty()){
            return playLeft.pop();
        }
       Tour lastTour = history.getLast();
        boolean opponentDecision =opponentPlayerNumber==1?lastTour.getPlayer1Decision():lastTour.getPlayer2Decision();
        if(opponentDecision){
            return true;
        }else {
            int betrayByOpponentCount= history.stream().filter(tour ->opponentPlayerNumber==1?!lastTour.getPlayer1Decision():!lastTour.getPlayer2Decision()).toList().size();
            playLeft.push(true);
            playLeft.push(true);
            for(int i=0; i<betrayByOpponentCount-1; i++){
                playLeft.push(false);
            }
            return false;
        }

    }
}


