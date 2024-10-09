package fr.uga.m1miage.pc.Partie.response;

import lombok.Data;

import java.util.UUID;

@Data
public class PartieDTO {
    private UUID id;
    private String statut;

}
