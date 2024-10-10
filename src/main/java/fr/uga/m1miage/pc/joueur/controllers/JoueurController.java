package fr.uga.m1miage.pc.joueur.controllers;

import fr.uga.m1miage.pc.jeu.requests.AbandonRequestDTO;
import fr.uga.m1miage.pc.joueur.mappers.JoueurMapper;
import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.joueur.responses.JoueurDTO;
import fr.uga.m1miage.pc.joueur.service.JoueurService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/joueurs")
@AllArgsConstructor
public class JoueurController {


    private final JoueurService joueurService;


    private final JoueurMapper joueurMapper;
    @PatchMapping("/{idJoueur}/abandonner")
    public ResponseEntity<JoueurDTO> abandonnerJeu(@RequestBody AbandonRequestDTO abandonRequestDTO, @PathVariable UUID idJoueur) {

        JoueurEntity joueur = joueurService.abandonnerJeu(idJoueur, abandonRequestDTO.getStrategie());

        JoueurDTO response = joueurMapper.mapEntityToDTO(joueur);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
