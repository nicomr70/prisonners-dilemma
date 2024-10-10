package fr.uga.m1miage.pc.Joueur.controllers;

import fr.uga.m1miage.pc.Jeu.requests.AbandonRequestDTO;
import fr.uga.m1miage.pc.Joueur.mappers.JoueurMapper;
import fr.uga.m1miage.pc.Joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.Joueur.responses.JoueurDTO;
import fr.uga.m1miage.pc.Joueur.service.JoueurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/joueurs")
public class JoueurController {

    @Autowired
    JoueurService joueurService;

    @Autowired
    JoueurMapper joueurMapper;
    @PatchMapping("/{idJoueur}/abandonner")
    public ResponseEntity<JoueurDTO> abandonnerJeu(@RequestBody AbandonRequestDTO abandonRequestDTO, @PathVariable UUID idJoueur) {

        JoueurEntity joueur = joueurService.abandonnerJeu(idJoueur, abandonRequestDTO.getStrategie());

        JoueurDTO response = joueurMapper.mapEntityToDTO(joueur);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
