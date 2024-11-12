package fr.uga.miage.m1.my_project.server.models.strategies;

import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;

import java.util.List;
// Toujours coopérer sans tenir en compte des réponses de l'adverse

public class ToujoursCoopererStrategie extends Strategie {
    @Override
    public TypeAction getAction(List<TypeAction> actions, int dernierResultat) {
        return TypeAction.COOPERER;
    }
}
