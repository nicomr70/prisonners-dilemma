package fr.uga.m1miage.pc.jeu.response;

import fr.uga.m1miage.pc.jeu.enums.StatutJeuEnum;
import fr.uga.m1miage.pc.joueur.responses.JoueurDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class JeuConnexionResponseDTO {
    private Long id;
    private StatutJeuEnum statut;
    private JoueurDTO joueurCree;
}
