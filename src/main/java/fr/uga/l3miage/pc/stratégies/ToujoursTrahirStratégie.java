package fr.uga.l3miage.pc.stratégies;

public class ToujoursTrahirStratégie implements Strategie {
    private String coupAJpuer;
    public ToujoursTrahirStratégie(){
        this.coupAJpuer = "t";
    }
    public String prochainCoup() {
        return "t";
    }
}
