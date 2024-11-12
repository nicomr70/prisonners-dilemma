package fr.uga.miage.m1.my_project.server.models.strategies;

import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;
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
