package fr.uga.miage.m1.my_project.server.models.strategies;

import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;

import java.util.List;
// Toujours trahir sans tenir en compte des réponses de l'adverse
public class ToujoursTrahirStrategie extends Strategie {
    @Override
    public TypeAction getAction(List<TypeAction> actions, int dernierResultat) {return TypeAction.TRAHIR;
    }
}
