package fr.uga.l3miage.pc.prisonersdilemma;

import java.io.IOException;

public class Partie {
    private Client client1;
    private Client client2;
    private int nombreTours;
    private Serveur serveur;

    public Partie(Client client1, Client client2,int nombreTours){//,BufferedReader inClient1,BufferedReader inClient2,PrintWriter outClient1,PrintWriter outClient2) {
    serveur= Serveur.getInstance();
    this.client1=client1;
    this.client2=client2;
    this.nombreTours=nombreTours;
    }


    public void commencer() throws IOException {
        for (int i = 1; i <= nombreTours; i++) {
            System.out.println("Tour " + i);
            serveur.askCoup(client1);
            serveur.askCoup(client2);
            serveur.calculScore();
            serveur.envoyerScores();

        }
    }
    }


