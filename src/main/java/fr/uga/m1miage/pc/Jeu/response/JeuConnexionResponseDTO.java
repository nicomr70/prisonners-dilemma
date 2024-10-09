package fr.uga.m1miage.pc.Jeu.response;

import fr.uga.m1miage.pc.Jeu.enums.StatutEnum;
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
    private StatutEnum statut;
}
