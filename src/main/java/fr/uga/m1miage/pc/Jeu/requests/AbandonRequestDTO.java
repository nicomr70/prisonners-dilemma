package fr.uga.m1miage.pc.Jeu.requests;

import fr.uga.m1miage.pc.Joueur.enums.StrategieEnum;
import lombok.Data;

import java.util.UUID;


@Data
public class AbandonRequestDTO {
    private UUID idJoueur;
    private Long idJeu;
    private StrategieEnum strategie;
}
