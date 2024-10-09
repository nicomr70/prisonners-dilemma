package fr.uga.l3miage.pc.prisonersdilemma;

import java.io.BufferedReader;
import java.io.IOException;

public class Partie {
    private String nomClient1;
    private String nomClient2;
    private int nombreTours;
    private Serveur serveur;

    public Partie() {
    serveur= Serveur.getInstance();
    }

    public void commencer() throws IOException {
        nombreTours = serveur.demanderNbTours();
        for (int i = 1; i <= nombreTours; i++) {
            System.out.println("Tour " + i);
        }
    }
    }


