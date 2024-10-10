package fr.uga.m1miage.pc.jeu.response;

import fr.uga.m1miage.pc.jeu.enums.StatutJeuEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JeuConnexionResponseDTO {
    private Long id;
    private StatutJeuEnum statut;
}
