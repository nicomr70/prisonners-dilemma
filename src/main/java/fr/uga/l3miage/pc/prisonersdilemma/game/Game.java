package fr.uga.l3miage.pc.prisonersdilemma.game;

import fr.uga.l3miage.pc.prisonersdilemma.Turn;
import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import fr.uga.l3miage.pc.prisonersdilemma.game.states.State;
import fr.uga.l3miage.pc.prisonersdilemma.game.states.WaitingState;
import lombok.Getter;

import java.util.*;

@Getter
public class Game {

    private String id;
    private Turn[] turns;
    private State state;
    private int maxTurns;
    private int currentTurn;

    public Game(int maxTurns){
        generateGameId();
        initTurns(maxTurns);
        this.state = new WaitingState(this);
        this.maxTurns = maxTurns;
        this.currentTurn = 0;
    }

    public void changeState(State state){
        this.state = state;
    }

    private void incrementTurn(){
        this.currentTurn++;
    }

    private void initTurns(int maxTurns) {
        this.turns = new Turn[maxTurns];
        for(int i = 0; i < maxTurns; i++){
            this.turns[i] = new Turn(Action.NONE,Action.NONE);
        }
    }

    public void playTurn(Action action, PlayerNumber playerNumber){

        this.turns[this.currentTurn].updateTurn(action, playerNumber);

        if(playerNumber == PlayerNumber.PLAYER_ONE){
            incrementTurn();
        }
    }

    public List<Action> getHistoryByPlayerNumber(PlayerNumber playerNumber){
        List<Action> history = new ArrayList<>();
        return null;
    }

    private void generateGameId() {
        this.id = UUID.randomUUID().toString();
    }

}
