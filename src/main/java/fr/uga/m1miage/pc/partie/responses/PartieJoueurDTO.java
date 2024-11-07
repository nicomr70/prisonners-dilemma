package fr.uga.m1miage.pc.partie.responses;

import fr.uga.m1miage.pc.joueur.responses.JoueurDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PartieJoueurDTO {
    private String coup;
    private int score;
    private JoueurDTO joueur;
    private PartieDTO partie;
}
