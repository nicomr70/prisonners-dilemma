package fr.uga.l3miage.pc.prisonersdilemma;

import java.io.IOException;
import java.util.Scanner;

public class Joueur {
    private String nom;
    private boolean abandon;
    private int score;

    public Joueur() {
        this.nom = null;
        this.abandon = false;
        this.score = 0;
    }
    public String jouerCoup(Client client) throws IOException {
        String coup = decision();
        client.envoyerCoup(coup);
        return coup;
    }

    public String decision() {
        Scanner scanner = new Scanner(System.in);
        String decision;
        do {
            System.out.print(nom + ", Voulez vous trahir (t = trahir) ou cooperer (c = coop?rer) ? ");
            decision = scanner.nextLine().toLowerCase();
        } while (!decision.equals("c") && !decision.equals("t"));
        return decision;
    }

    public void abandonner(){
        this.abandon = true;
        Scanner scanner = new Scanner(System.in);
        String stratVoulue;
        do {
            System.out.print(nom + ", Vous avez decid? d'abandonner. Vous devez maintenant choisir une de ces strat?gie : 1- Donnant-Donnant (dd) 2- Toujours Trahir (t) 3- Toujours Coop?rer (c) 4- Rancunier (r) 5- Pavlov (p) ");
            stratVoulue = scanner.nextLine().toLowerCase();
        } while (!stratVoulue.equals("dd") && !stratVoulue.equals("t") && !stratVoulue.equals("c") && !stratVoulue.equals("r") && !stratVoulue.equals("p") );
        //Il faudra faire appel a la factory en fonction du choix du joueur, ?a sera envoy? au client par la suite.
    }

    public int getScore() {
        return score;
    }

    public String getNom() {
        return nom;
    }

    public void setNom() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nom = ");
        this.nom = scanner.nextLine().toLowerCase();
    }

    public String getNbTours(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nb Tours = ");
        return scanner.nextLine();
    }

}
