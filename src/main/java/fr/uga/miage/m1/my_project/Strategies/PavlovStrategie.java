package fr.uga.miage.m1.my_project.Strategies;

import fr.uga.miage.m1.my_project.Enums.TypeAction;


import java.util.List;


public class PavlovStrategie extends Strategie {

    public PavlovStrategie(String name) {
        super(name);
    }

    @Override
    public TypeAction getAction(List<TypeAction> actions, int dernierResultat) {
        // Vérifier si la liste est nulle ou vide
        if (actions == null || actions.isEmpty()) {
            // Définir une action par défaut, par exemple, COOPERER
            return TypeAction.COOPERER;
        }

        TypeAction lastAction = actions.get(actions.size() - 1);

        if (dernierResultat == 5 || dernierResultat == 3) {
            return lastAction;
        } else {
            return (lastAction == TypeAction.TRAHIR) ? TypeAction.TRAHIR : TypeAction.COOPERER;
        }
    }
}