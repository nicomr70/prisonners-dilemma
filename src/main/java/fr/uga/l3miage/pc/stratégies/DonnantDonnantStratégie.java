package fr.uga.l3miage.pc.stratégies;

public class DonnantDonnantStratégie {
    private String dernierCoupAdversaire;

    public DonnantDonnantStratégie() {
        this.dernierCoupAdversaire = "c";
    }

    public String prochainCoup(){
        return dernierCoupAdversaire;
    }

    public void miseAJourDernierCoupAdversaire(String coupAdversaire){
        this.dernierCoupAdversaire = coupAdversaire;
    }
}
