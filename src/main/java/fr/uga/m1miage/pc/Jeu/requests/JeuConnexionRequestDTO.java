package fr.uga.m1miage.pc.Jeu.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JeuConnexionRequestDTO {
    private String pseudoJoueur;
    private Long codeJeu;
}
