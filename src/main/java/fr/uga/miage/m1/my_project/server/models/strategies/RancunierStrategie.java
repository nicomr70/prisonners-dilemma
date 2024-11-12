package fr.uga.miage.m1.my_project.server.models.strategies;

import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;
import java.util.List;

public class RancunierStrategie extends Strategie{

    @Override
    public TypeAction getAction(List<TypeAction> actions, int dernierResultat) {
        // On vérifie le dernier coup de l'adverssaire
        if (!actions.isEmpty() && getLastAction(actions) == TypeAction.TRAHIR) {
            return TypeAction.TRAHIR;
        }
        // Sinon, nous coopérons
        return TypeAction.COOPERER;
    }
}
