package fr.uga.m1miage.pc.joueur.responses;

import fr.uga.m1miage.pc.joueur.enums.StrategieEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoueurDTO {
    private UUID id;
    private String nom;
    private StrategieEnum strategie;
    private Boolean abandon;
}
