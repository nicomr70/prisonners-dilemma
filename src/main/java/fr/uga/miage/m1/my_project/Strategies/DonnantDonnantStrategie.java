package fr.uga.miage.m1.my_project.Strategies;

import fr.uga.miage.m1.my_project.Enums.TypeAction;
import java.util.List;

public class DonnantDonnantStrategie extends Strategie {

    // Jouer comme le dernier coup de l'adversaire
    @Override
    public TypeAction getAction(List<TypeAction> actions, int dernierResultat) {
        if (actions.isEmpty()) {
            return TypeAction.COOPERER;
        }
        return getLastAction(actions);
    }
}
