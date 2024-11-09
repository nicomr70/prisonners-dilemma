package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Action;
import fr.uga.l3miage.pc.prisonersdilemma.enums.PlayerNumber;
import org.w3c.dom.ls.LSInput;

import java.util.*;

public class Game {

    private String id;
    private Turn[] turns;

    public Game(int maxTurns){
        generateGameId();
        initTurns(maxTurns);
    }

    private void initTurns(int maxTurns) {
        this.turns = new Turn[maxTurns];
        for(int i = 0; i < maxTurns; i++){
            this.turns[i] = new Turn(Action.NONE,Action.NONE);
        }
    }

    public Turn[] getTurns(){
        return this.turns;
    }

    public void playTurn(Action action, int turnNumber, PlayerNumber playerNumber){
        this.turns[turnNumber].updateTurn(action, playerNumber);
    }
// TODO
    public List<Action> getHistoryByPlayerNumber(PlayerNumber playerNumber){
        List<Action> history = new ArrayList<>();
        return null;
    }

    private void generateGameId() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
}
