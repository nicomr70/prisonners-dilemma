package fr.uga.l3miage.pc.stratégies;

public class StrategieFactory {
    public static Strategie getStrategie(String choixStrategie) {
        switch (choixStrategie) {
            case "dd":
                return new DonnantDonnantStratégie();
            case "t":
                return new ToujoursTrahirStratégie();
            case "c":
                return new ToujoursCoopérer();
            case "r":
                return new RancunierStratégie();
            case "p":
                return new PavlovStratégie();
            default:
                throw new IllegalArgumentException("Stratégie inconnue : " + choixStrategie);
        }
    }
}
