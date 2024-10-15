package fr.uga.miage.m1.my_project.server.models;

import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;
import fr.uga.miage.m1.my_project.server.models.strategies.Strategie;
import fr.uga.miage.m1.my_project.server.models.strategies.ToujoursCoopererStrategie;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Robot extends Joueur {

    public Robot(String id, String nom, int score) throws Exception {
        super(id, nom);
        this.score = score;
    }

    public Robot(String id, String nom, int score, Strategie strategie) throws Exception {
        this(id, nom, score);
        this.strategieAutomatique = strategie;
    }

    @Override
    public TypeAction jouer(List<TypeAction> historiqueAdversaire, int dernierResultat) throws Exception {
        if (strategieAutomatique != null) {
            return strategieAutomatique.getAction(historiqueAdversaire, dernierResultat);
        }
        return TypeAction.COOPERER; // Valeur par défaut
    }

    // Méthodes spécifiques aux robots peuvent être ajoutées ici
}