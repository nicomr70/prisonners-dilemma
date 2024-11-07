package fr.uga.m1miage.pc.jeu.response;

import fr.uga.m1miage.pc.jeu.enums.StatutJeuEnum;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class JeuCreationResponseDTO {
    private Long id;
    private StatutJeuEnum statut;
    private int nombreParties;
}
