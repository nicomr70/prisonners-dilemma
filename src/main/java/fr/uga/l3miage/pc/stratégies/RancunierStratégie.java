package fr.uga.l3miage.pc.stratégies;

public class RancunierStratégie implements Strategie {
    private int index;
    private String[] historique;

    public RancunierStratégie(String[] historique) {
        this.historique = historique;
        this.index = 0;
    }

    public String prochainCoup() {
        return index > 0 && historique[index - 1].equals("t") ? "t" : "c";
    }

    public void miseAJourDernierCoupAdversaire(String coupAdversaire) {
        historique[index++] = coupAdversaire;
    }
}
