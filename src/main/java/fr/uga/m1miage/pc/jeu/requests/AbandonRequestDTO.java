package fr.uga.m1miage.pc.jeu.requests;

import fr.uga.m1miage.pc.joueur.enums.StrategieEnum;
import lombok.Data;

@Data
public class AbandonRequestDTO {
    private StrategieEnum strategie;
}
