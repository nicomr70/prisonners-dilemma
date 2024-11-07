package fr.uga.m1miage.pc.jeu.requests;

import fr.uga.m1miage.pc.joueur.enums.StrategieEnum;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AbandonRequestDTO {
    private StrategieEnum strategie;
}
