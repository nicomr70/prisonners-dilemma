package fr.uga.l3miage.pc.prisonersdilemma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Partie {
    private Client client1;
    private Client client2;
    private int nombreTours;
    private Serveur serveur;
 /*   private BufferedReader inClient1;
    private BufferedReader inClient2;
    private PrintWriter outClient1;
    private PrintWriter outClient2;*/

    public Partie(Client client1, Client client2,int nombreTours){//,BufferedReader inClient1,BufferedReader inClient2,PrintWriter outClient1,PrintWriter outClient2) {
    serveur= Serveur.getInstance();
    this.client1=client1;
    this.client2=client2;
    this.nombreTours=nombreTours;
    /*this.inClient1=inClient1;
    this.inClient2=inClient2;
    this.outClient1=outClient1;
    this.outClient2=outClient2;*/
    }


    public void commencer() throws IOException {
        for (int i = 1; i <= nombreTours; i++) {
            System.out.println("Tour " + i);
            serveur.askCoup(client1);
            serveur.askCoup(client2);
            serveur.calculScore();

        }
    }
    }


