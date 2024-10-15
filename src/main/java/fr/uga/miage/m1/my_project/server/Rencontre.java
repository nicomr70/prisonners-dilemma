package fr.uga.miage.m1.my_project.server;

import fr.uga.miage.m1.my_project.server.models.Humain;
import fr.uga.miage.m1.my_project.server.models.Joueur;
import fr.uga.miage.m1.my_project.server.models.Robot;
import fr.uga.miage.m1.my_project.server.models.enums.EtatJoueur;
import fr.uga.miage.m1.my_project.server.models.enums.TypeStrategie;
import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;
import fr.uga.miage.m1.my_project.server.models.strategies.*;
import lombok.Data;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Data
public class Rencontre extends Thread {
    private Joueur initiateur;
    private Joueur adversaire;
    private int nombreTours;
    private List<TypeAction> historiqueJ1;
    private List<TypeAction> historiqueJ2;
    private List<Tour> tours;
    
    // Liste statique pour les rencontres en attente
    private static List<Rencontre> rencontresEnAttente = new ArrayList<>();


    Rencontre() {
        this.initiateur = null;
        this.adversaire = null;
        this.nombreTours = 0;
        this.historiqueJ1 = new ArrayList<>();
        this.historiqueJ2 = new ArrayList<>();
        this.tours = new ArrayList<>();
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
                if (initiateur instanceof Humain) {
                    initiateur.sendMessage("Tour " + i);
                }

                if (adversaire instanceof Humain) {
                    adversaire.sendMessage("Tour " + i);
                }

                TypeAction action1 = initiateur.jouer(historiqueJ2, 0);
                TypeAction action2 = adversaire.jouer(historiqueJ1, 0);

                historiqueJ1.add(action1);
                historiqueJ2.add(action2);

                // ici on doit distinguer entre rebot et humain...

                if (action1 == TypeAction.ABONDONNER) {
                    Strategie strategie = getStrategie(initiateur);
                    initiateur.close();
                    initiateur = new Robot(initiateur.getId() + "_ai", initiateur.getNom() + "_ai", initiateur.getScore(), strategie);

                    // Vérifier si le tableau tours est vide avant d'accéder à l'élément
                    if (!tours.isEmpty()) {
                        action1 = initiateur.jouer(historiqueJ2, tours.get(tours.size() - 1).getScoreInitiateur());
                    } else {
                        // Initialiser une valeur par défaut si tours est vide
                        action1 = initiateur.jouer(historiqueJ2, 0);  // Exemple avec score par défaut de 0
                    }

                }
                else if (action2 == TypeAction.ABONDONNER) {
                    Strategie strategie = getStrategie(adversaire);
                    adversaire.close();
                    adversaire = new Robot(adversaire.getId() + "_ai", adversaire.getNom() + "_ai", adversaire.getScore(), strategie);
                    // Vérifier si le tableau tours est vide avant d'accéder à l'élément
                    if (!tours.isEmpty()) {
                        action2 = adversaire.jouer(historiqueJ1, tours.get(tours.size() - 1).getScoreAdversaire());
                    } else {
                        // Initialiser une valeur par défaut si tours est vide
                        action2 = adversaire.jouer(historiqueJ1, 0);  // Exemple avec score par défaut de 0
                    }
                }

                Tour tour = new Tour(action1, action2);
                tours.add(tour);


                if (initiateur instanceof Humain) {
                    initiateur.addScore(tour.getScoreInitiateur());
                }
                if (adversaire instanceof Humain) {
                    adversaire.addScore(tour.getScoreAdversaire());
                }

                // Envoyer les résultats

                if (initiateur instanceof Humain) {
                    initiateur.sendMessage("Résultat du tour: Vous avez " + action1 + ", l'adversaire a " + action2 + ".");
                    initiateur.sendMessage("Votre score pour ce tour: " + tour.getScoreInitiateur() + ". Score total: " + initiateur.getScore() + ".");
                }


                if (adversaire instanceof Humain) {
                    adversaire.sendMessage("Résultat du tour: Vous avez " + action2 + ", l'adversaire a " + action1 + ".");
                    adversaire.sendMessage("Votre score pour ce tour: " + tour.getScoreAdversaire() + ". Score total: " + adversaire.getScore() + ".");
                }
            }

            // Fin de la rencontre
            if (initiateur instanceof Humain) {
                initiateur.sendMessage("Rencontre terminée. Score final: " + initiateur.getScore());
            }
            if (adversaire instanceof Humain) {
                adversaire.sendMessage("Rencontre terminée. Score final: " + adversaire.getScore());
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (initiateur instanceof Humain) {
                initiateur.close();
            }
            if (adversaire instanceof Humain) {
                adversaire.close();
            }
        }
    }

    private Strategie getStrategie(Joueur joueur) throws Exception {
        joueur.sendMessage("choisir une strategie automatique parmi : [DonnantDonnantAleatoire, DonnantDonnant, PavlovStrategie, RancunierStrategie, ToujoursCooperer] ");
        TypeStrategie TypeStrategie = (TypeStrategie) joueur.receiveMessage();
        Strategie strategie = null;
        switch (TypeStrategie) {
            case DONNANTDONNANT -> {
                strategie = new DonnantDonnantStrategie();
            }
            case DONNANTDONNANTALEATOIRE -> strategie = new DonnantDonnantAleatoireStrategie(new SecureRandom());
            case PAVLOVSTRATEGIE -> strategie = new PavlovStrategie();
            case RANCUNIERSTRATEGIE -> strategie = new RancunierStrategie();
            case TOUJOURSCOOPERER -> strategie = new ToujoursCoopererStrategie();
        }
        return strategie;
    }
}