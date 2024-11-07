package fr.uga.m1miage.pc.partie.responses;

import fr.uga.m1miage.pc.partie.enums.StatutPartieEnum;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class PartieDTO {
    private UUID id;
    private StatutPartieEnum statut;
    private int ordre;
}
