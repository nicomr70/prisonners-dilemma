package fr.uga.m1miage.pc.partie.controllers;

import fr.uga.m1miage.pc.partie.mappers.PartieJoueurMapper;
import fr.uga.m1miage.pc.partie.mappers.PartieMapper;
import fr.uga.m1miage.pc.partie.models.PartieEntity;
import fr.uga.m1miage.pc.partie.models.PartieJoueurEntity;
import fr.uga.m1miage.pc.partie.requests.CoupRequest;
import fr.uga.m1miage.pc.partie.responses.PartieDetailsDTO;
import fr.uga.m1miage.pc.partie.responses.PartieJoueurDTO;
import fr.uga.m1miage.pc.partie.service.PartieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/parties")
public class PartieController {
    @Autowired
    private PartieService partieService;

    @Autowired
    private PartieJoueurMapper partieJoueurMapper;

    @Autowired
    private PartieMapper partieMapper;

    @PostMapping("/{idJeu}/joueurs/{idJoueur}/jouer-coup")
    public ResponseEntity<PartieJoueurDTO> jouerCoup(
            @PathVariable Long idJeu,
            @PathVariable UUID idJoueur,
            @RequestBody CoupRequest coupRequest
    ) {
        PartieJoueurEntity partieJoueur = partieService.joueurCoup(idJoueur,idJeu,coupRequest.getCoup());

        PartieJoueurDTO partieJoueurDTO = partieJoueurMapper.toDto(partieJoueur);
        return ResponseEntity.ok(partieJoueurDTO);
    }


    @GetMapping("{idPartie}/details")
    public ResponseEntity<PartieDetailsDTO> recupererDetailsPartie(@PathVariable UUID idPartie) {
        PartieEntity partie = partieService.recupererDetailsPartie(idPartie);
        PartieDetailsDTO response = partieMapper.mapEntityToDetailsDTO(partie);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
