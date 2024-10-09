package fr.uga.l3miage.pc.stratégies;

public class StrategieFactory {
    public static Strategie getStrategie(String choixStrategie, String[] historique) {
        switch (choixStrategie) {
            case "dd":
                return new DonnantDonnantStratégie(historique);
            case "t":
                return new ToujoursTrahirStratégie();
            case "c":
                return new ToujoursCoopérer();
            case "r":
                return new RancunierStratégie(historique);
            case "p":
                return new PavlovStratégie(historique);
            default:
                throw new IllegalArgumentException("Stratégie inconnue : " + choixStrategie);
        }
    }
}
