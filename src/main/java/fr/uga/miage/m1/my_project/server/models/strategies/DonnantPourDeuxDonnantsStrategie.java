package fr.uga.miage.m1.my_project.server.models.strategies;

import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;
import java.util.List;

public class DonnantPourDeuxDonnantsStrategie extends Strategie {

    @Override
    public TypeAction getAction(List<TypeAction> actions, int dernierResultat) {
        // Si aucune action précédente, coopère par défaut
        if (actions.isEmpty()) {
            return TypeAction.COOPERER;
        }

        // Si l'adversaire a coopéré deux fois consécutives, coopère
        if (actions.size() > 1 && actions.get(actions.size() - 1) == TypeAction.COOPERER
                && actions.get(actions.size() - 2) == TypeAction.COOPERER) {
            return TypeAction.COOPERER;
        }

        // Sinon, trahit
        return TypeAction.TRAHIR;
    }
}
