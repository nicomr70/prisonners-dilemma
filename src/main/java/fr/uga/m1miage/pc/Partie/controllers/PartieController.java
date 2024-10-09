package fr.uga.m1miage.pc.Partie.controllers;

import fr.uga.m1miage.pc.Partie.mappers.PartieJoueurMapper;
import fr.uga.m1miage.pc.Partie.models.PartieJoueurEntity;
import fr.uga.m1miage.pc.Partie.requests.CoupRequest;
import fr.uga.m1miage.pc.Partie.responses.PartieJoueurDTO;
import fr.uga.m1miage.pc.Partie.service.PartieService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/parties")
@NoArgsConstructor
@AllArgsConstructor
public class PartieController {
    @Autowired
    private PartieService partieService;

    @Autowired
    private PartieJoueurMapper partieJoueurMapper;
    @PostMapping("/jeux/{idJeu}/joueurs/{idJoueur}/jouer-coup")
    public ResponseEntity<PartieJoueurDTO> jouerCoup(
            @PathVariable Long idJeu,
            @PathVariable UUID idJoueur,
            @RequestBody CoupRequest coupRequest
    ) {
        PartieJoueurEntity partieJoueur = partieService.joueurCoup(idJoueur,idJeu,coupRequest.getCoup());

        PartieJoueurDTO partieJoueurDTO = partieJoueurMapper.toDto(partieJoueur);
        return ResponseEntity.ok(partieJoueurDTO);

    }
}
