package fr.uga.miage.m1.my_project.server.models.strategies;

import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;
import java.util.List;

public class AdaptatifStrategie extends Strategie {

    private int coupCount = 0; // Compteur pour suivre le nombre de tours
    private double scoreC = 0; // Score total pour 'COOPERER'
    private double scoreT = 0; // Score total pour 'TRAHIR'
    private int countC = 0; // Nombre de fois où 'COOPERER' a été choisi
    private int countT = 0; // Nombre de fois où 'TRAHIR' a été choisi
    private final double[] sequenceInitiale = {
            0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 // La séquence initiale c, c, c, c, c, c, t, t, t, t, t
    };

    @Override
    public TypeAction getAction(List<TypeAction> actions, int dernierResultat) {
        // Si la séquence initiale n'est pas encore terminée, on suit la séquence
        if (coupCount < sequenceInitiale.length) {
            coupCount++;
            return sequenceInitiale[coupCount - 1] == 0 ? TypeAction.COOPERER : TypeAction.TRAHIR;
        }

        // Après la séquence initiale, on choisit l'action ayant le meilleur score moyen
        double moyenneC = countC > 0 ? scoreC / countC : 0;
        double moyenneT = countT > 0 ? scoreT / countT : 0;

        // Choisir l'action avec le meilleur score moyen
        if (moyenneC > moyenneT) {
            return TypeAction.COOPERER;
        } else {
            return TypeAction.TRAHIR;
        }
    }

    public void updateScores(int dernierResultat, TypeAction action) {
        // Met à jour les scores pour COOPERER ou TRAHIR
        if (action == TypeAction.COOPERER) {
            scoreC += dernierResultat;
            countC++;
        } else {
            scoreT += dernierResultat;
            countT++;
        }
    }
}
