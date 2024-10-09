package fr.uga.m1miage.pc.Joueur.responses;

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
    private String nomJoueur;
}
