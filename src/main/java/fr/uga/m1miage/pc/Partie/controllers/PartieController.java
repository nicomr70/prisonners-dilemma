package fr.uga.m1miage.pc.Partie.controllers;

import fr.uga.m1miage.pc.Partie.models.PartieJoueurEntity;
import fr.uga.m1miage.pc.Partie.request.CoupRequest;
import fr.uga.m1miage.pc.Partie.service.PartieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/jeux")
public class PartieController {
    @Autowired
    private PartieService partieService;

    @PostMapping("/{idJeu}/joueurs/{idJoueur}/jouer-coup")
    public ResponseEntity<PartieJoueurEntity> jouerCoup(
            @PathVariable Long idJeu,
            @PathVariable UUID idJoueur,
            @RequestBody CoupRequest coupRequest
    ) {
        PartieJoueurEntity partieJoueur = partieService.joueurCoup(idJoueur,idJeu,coupRequest.getCoup());
        return ResponseEntity.ok(partieJoueur);

    }
}
