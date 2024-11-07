package fr.uga.m1miage.pc.joueur.strategies;

import fr.uga.m1miage.pc.joueur.enums.StrategieEnum;
public class StrategieFactory {

    private StrategieFactory(){}

    public static StrategieInterface getStrategie(StrategieEnum strategieEnum) {
        switch (strategieEnum) {
            case TOUJOURS_TRAHIR : return new ToujoursTrahirStrategie();

            case TOUJOURS_COOPERER : return new ToujoursCoopererStrategie();

            case ALEATOIRE: return new AleatoireStrategie();

            case DONNANT_DONNANT: return  new DonnantDonnant();

            case RANCUNIER: return new RancunierStrategie();

            default:
                throw new IllegalArgumentException("Stratégie inconnue : " + strategieEnum);

        }
    }
}
