package fr.uga.miage.m1.my_project.Strategies;

import fr.uga.miage.m1.my_project.Enums.TypeAction;

import java.util.List;
import java.util.Random;

// Jouer comme le dernier coup de l'adversaire, mais jouer parfois un coup au hasard
public class DonnantDonnantAleatoireStrategie extends Strategie{
    private Random random = new Random();

    // Méthode pour obtenir une action aléatoire (coopérer ou trahir)
    private TypeAction getRandomAction() {
        return random.nextBoolean() ? TypeAction.COOPERER : TypeAction.TRAHIR;
    }

    @Override
    public TypeAction getAction(List<TypeAction> actions, int dernierResultat) {
        //décider de la stratégie à utiliser
        boolean useRandomAction = random.nextBoolean(); // Renvoie true ou false de façon aléatoire

        if (useRandomAction) {
            return getRandomAction();
        } else {
            return getLastAction(actions); // Dernier élément de la liste
        }
    }




}
