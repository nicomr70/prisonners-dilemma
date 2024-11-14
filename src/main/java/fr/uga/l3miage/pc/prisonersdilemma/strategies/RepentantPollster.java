package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RepentantPollster implements Strategy{

    private final Random random;
    @Getter
    private final List<Action> strategyHistory;


    public RepentantPollster(Random random) {
        this.random = random;
        this.strategyHistory = new ArrayList<>();
    }
    @Override
    public Action play(List<Action> opponentHistory){

        if (opponentHistory.isEmpty()) {
            Action response = Action.COOPERATE;
            addActionInStrategyHistory(response);
            return response;
        }
        if (opponentHasBetrayedAfterRandomBetray(opponentHistory)){
            Action response = Action.COOPERATE;
            addActionInStrategyHistory(response);
            return response;
        }
        if (isNextActionRandom() ){
            Action response = Action.BETRAY;
            addActionInStrategyHistory(response);
            return response;
        }
        Action response = opponentHistory.get(opponentHistory.size()-1);
        addActionInStrategyHistory(response);
        return response;
    }
    private boolean isNextActionRandom() {
        int randomInt = random.nextInt(2);
        return randomInt == 1;
    }

    private boolean opponentHasBetrayedAfterRandomBetray(List<Action> opponentHistory){
        return (opponentHistory.get(opponentHistory.size()-1) == this.strategyHistory.get(strategyHistory.size()-1)) && (this.strategyHistory.get(strategyHistory.size()-1) == Action.BETRAY);
    }

    private void addActionInStrategyHistory(Action action){
        this.strategyHistory.add(action);
    }

}
