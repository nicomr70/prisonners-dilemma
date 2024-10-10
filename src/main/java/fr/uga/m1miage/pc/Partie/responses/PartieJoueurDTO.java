package fr.uga.m1miage.pc.Partie.responses;

import fr.uga.m1miage.pc.Joueur.responses.JoueurDTO;
import lombok.Data;

@Data
public class PartieJoueurDTO {
    private String coup;
    private int score;
    private JoueurDTO joueur;
    private PartieDTO partie;
}
