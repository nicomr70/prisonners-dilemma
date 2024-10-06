package fr.uga.l3miage.pc.prisonersdilemma.models;


import fr.uga.l3miage.pc.prisonersdilemma.utils.Utils;
import jdk.jshell.execution.Util;

import java.util.Scanner;
import java.util.SequencedCollection;

import static fr.uga.l3miage.pc.prisonersdilemma.utils.Utils.chooseStrategy;

public class Player  implements  PlayerInterface{
    private  String name;
    private int score=0;
    private Strategy strategy;
    private boolean AI=false;
    private GameEncounter gameEncounter;

    public Player(String name, GameEncounter gameEncounter, Strategy strategy){
        this.name=name;
        this.gameEncounter=gameEncounter;
        setStrategy(strategy);

    }

    private  void setStrategy(Strategy strategy){
            this.strategy=strategy;
    }

    @Override
    public boolean makeDecision() {
        if(!AI){
            //Player makes decision
            System.out.println("Player "+name +" :  make decision (True: to cooperate | False: to betray");
            Scanner scanner = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter decision : ");
            String playerDecision = scanner.nextLine();
            while(!playerDecision.toLowerCase().equals("true") || !playerDecision.toLowerCase().equals("false")){
                System.out.println("Enter a valide decision true/false : ");
                playerDecision = scanner.nextLine();
            }
            scanner.close();
            return Boolean.parseBoolean(playerDecision);

        }else {
            // AI makes decision
            int opponentPlayerNumber= gameEncounter.getPlayer1Name()==name ? 2:1;
            return strategy.play(gameEncounter.getHistory(), opponentPlayerNumber);
        }
    }

    @Override
    public int getScore() {
        return score;
    }
    public void updateScore(int points){
        this.score+=points;
    }
    public String getName(){
        return name;
    }



    @Override
    public void leaveEncounter() {
        AI=true;
        Utils.displayStrategiesMenu();
        int strategyNumber=chooseStrategy(name);
        Strategy strategy;
        switch (strategyNumber){
            case 1:
               // strategy = new DonnantDonnatFactory();
                break;
            case 2:
                //strategy = new DonnantDonnatRandomFactory();
                break;

        }
    }
}
