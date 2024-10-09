package fr.uga.l3miage.pc.stratégies;

public class RancunierStratégie {
    private boolean adversaireEstTraitre;

    //On supose que de base l'adversaire n'a pas trahis
    public RancunierStratégie(){
        this.adversaireEstTraitre = false;
    }

    public String prochainCoup(){
        if (adversaireEstTraitre){
            return "t";
        }else {
            return "c";
        }
    }

    public void miseAJourDernierCoupAdversaire(String coupAdversaire){
        if (coupAdversaire.equals("t")){
            adversaireEstTraitre = true;
        }else{
            adversaireEstTraitre = false;
        }
    }

}
