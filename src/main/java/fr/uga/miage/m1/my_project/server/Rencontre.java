package fr.uga.miage.m1.my_project.server;

import fr.uga.miage.m1.my_project.server.models.Joueur;
import fr.uga.miage.m1.my_project.server.models.enums.EtatJoueur;
import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Rencontre extends Thread {
    private Joueur initiateur;
    private Joueur adversaire;
    private int nombreTours;
    private List<TypeAction> historiqueJ1;
    private List<TypeAction> historiqueJ2;

    // Liste statique pour les rencontres en attente
    private static List<Rencontre> rencontresEnAttente = new ArrayList<>();


    Rencontre() {
        this.initiateur = null;
        this.adversaire = null;
        this.nombreTours = 0;
        this.historiqueJ1 = new ArrayList<>();
        this.historiqueJ2 = new ArrayList<>();
    }
    
    Rencontre(Joueur initiateur,int nombreTours) {
        this();
        this.initiateur = initiateur;
        this.nombreTours = nombreTours;
    }
    
    Rencontre(Joueur initiateur, Joueur adversaire, int nombreTours) {
        this();
        this.initiateur = initiateur;
        this.adversaire = adversaire;
    }
    // Méthode synchronisée pour ajouter une rencontre en attente
    public static synchronized void addRencontreEnAttente(Rencontre rencontre) {
        rencontresEnAttente.add(rencontre);
    }

    // Méthode synchronisée pour récupérer et supprimer la première rencontre en attente
    public static synchronized List<Rencontre> getRencontresEnAttente() {
        return rencontresEnAttente;
    }

    @Override
    public void run() {
        try {
            initiateur.setEtat(EtatJoueur.EN_PARTIE);
            adversaire.setEtat(EtatJoueur.EN_PARTIE);
            initiateur.sendMessage("Rencontre commencée avec " + adversaire.getNom());
            adversaire.sendMessage("Rencontre commencée avec " + initiateur.getNom());

            for (int i = 1; i <= nombreTours; i++) {
                initiateur.sendMessage("Tour " + i);
                adversaire.sendMessage("Tour " + i);

                //
                TypeAction action1 = initiateur.jouer(historiqueJ2, 0);
                TypeAction action2 = adversaire.jouer(historiqueJ1, 0);

                historiqueJ1.add(action1);
                historiqueJ2.add(action2);

                // ici on doit distinguer entre rebot et humain...

                Tour tour = new Tour(action1, action2);

                initiateur.addScore(tour.getScoreInitiateur());
                adversaire.addScore(tour.getScoreAdversaire());

                // Envoyer les résultats
                initiateur.sendMessage("Résultat du tour: Vous avez " + action1 + ", l'adversaire a " + action2 + ".");
                initiateur.sendMessage("Votre score pour ce tour: " + tour.getScoreInitiateur() + ". Score total: " + initiateur.getScore() + ".");

                adversaire.sendMessage("Résultat du tour: Vous avez " + action2 + ", l'adversaire a " + action1 + ".");
                adversaire.sendMessage("Votre score pour ce tour: " + tour.getScoreAdversaire() + ". Score total: " + adversaire.getScore() + ".");
            }

            // Fin de la rencontre
            initiateur.sendMessage("Rencontre terminée. Score final: " + initiateur.getScore());
            adversaire.sendMessage("Rencontre terminée. Score final: " + adversaire.getScore());



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            initiateur.close();
            adversaire.close();
        }
    }
}