package fr.uga.m1miage.pc.Jeu.response;

import fr.uga.m1miage.pc.Jeu.enums.StatutJeuEnum;
import fr.uga.m1miage.pc.Joueur.responses.JoueurDTO;
import fr.uga.m1miage.pc.Partie.responses.PartieDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JeuDTO {
    private Long id;
    private int nombreParties;
    private StatutJeuEnum statut;
    private List<PartieDTO> parties;
    private List<JoueurDTO> joueurs;
}
