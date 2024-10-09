package fr.uga.l3miage.pc.stratégies;

public class ToujoursCoopérer implements Strategie{

    private String coupAJouer;

    public ToujoursCoopérer(){
        this.coupAJouer = "c";
    }

    public String prochainCoup() {
        return "c";
    }
}
