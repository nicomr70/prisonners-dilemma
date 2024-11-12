package fr.uga.miage.m1.my_project.server.models;

import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;
import fr.uga.miage.m1.my_project.server.models.strategies.Strategie;
import java.util.List;

public class Robot extends Joueur {

    public Robot(String id, String nom, int score) {
        super(id, nom);
        this.score = score;
    }

    public Robot(String id, String nom, int score, Strategie strategie) {
        this(id, nom, score);
        this.strategieAutomatique = strategie;
    }

    @Override
    public TypeAction jouer(List<TypeAction> historiqueAdversaire, int dernierResultat) {
        if (strategieAutomatique != null) {
            return strategieAutomatique.getAction(historiqueAdversaire, dernierResultat);
        }
        return TypeAction.COOPERER; // Valeur par défaut
    }

    // Méthodes spécifiques aux robots peuvent être ajoutées ici
}