package fr.uga.l3miage.pc.prisonersdilemma.models;

import java.util.ArrayList;
import java.util.List;

public  class GameEncounter {
    private int nbTours;
    private Player player1;
    private Player player2;
    private String winner;

    private List<Tour> history;


    public GameEncounter(int n,Player p1,Player p2){
        this.nbTours=n;
        this.player1=p1;
        this.player2=p2;
        this.history=new ArrayList<>();


    }

    public String getWinner(){
        return this.winner;
    }
    public List<Tour> getHistory(){
        return history;
    }
    public boolean isFinished(){
        return this.winner!=null;
    }

}
