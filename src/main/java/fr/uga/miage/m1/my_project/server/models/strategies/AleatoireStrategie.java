package fr.uga.miage.m1.my_project.server.models.strategies;

import fr.uga.miage.m1.my_project.server.models.enums.TypeAction;
import java.util.List;
import java.security.SecureRandom;

public class AleatoireStrategie extends Strategie {

    public AleatoireStrategie(SecureRandom random) {
        super(random);
    }

    private TypeAction getRandomAction() {
        return this.getRandom().nextBoolean() ? TypeAction.COOPERER : TypeAction.TRAHIR;
    }

    @Override
    public TypeAction getAction(List<TypeAction> actions, int dernierResultat) {
        // Choisir aléatoirement entre traîr ou coopérer
        return getRandomAction();
    }
}
