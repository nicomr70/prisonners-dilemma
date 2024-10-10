package fr.uga.m1miage.pc.partie.responses;

import fr.uga.m1miage.pc.partie.enums.StatutPartieEnum;
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
    private int ordre;
}
