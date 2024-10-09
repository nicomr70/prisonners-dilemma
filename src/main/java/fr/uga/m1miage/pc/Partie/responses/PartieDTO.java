package fr.uga.m1miage.pc.Partie.responses;

import fr.uga.m1miage.pc.Jeu.models.JeuEntity;
import fr.uga.m1miage.pc.Partie.enums.StatutPartieEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartieDTO {
    private UUID id;
    private StatutPartieEnum statut;
}
