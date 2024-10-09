package fr.uga.m1miage.pc.Jeu.response;

import fr.uga.m1miage.pc.Jeu.enums.StatutJeuEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JeuCreationResponseDTO {
    private Long id;
    private StatutJeuEnum statut;
    private int nombreParties;
}
