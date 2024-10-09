package fr.uga.m1miage.pc.Jeu.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JeuCreationDTO {
    private String pseudoJoueur;
    private int nombreParties;
}
