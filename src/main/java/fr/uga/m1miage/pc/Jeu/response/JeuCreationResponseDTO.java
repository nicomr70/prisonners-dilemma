package fr.uga.m1miage.pc.Jeu.response;

import fr.uga.m1miage.pc.Jeu.enums.StatutEnum;
import fr.uga.m1miage.pc.Joueur.reponse.JoueurDTO;

import java.util.List;

public class JeuCreationResponseDTO {
    private Long id;
    private StatutEnum statut;
    private int nombreParties;

    private List<JoueurDTO> joueurDTOS;
}
