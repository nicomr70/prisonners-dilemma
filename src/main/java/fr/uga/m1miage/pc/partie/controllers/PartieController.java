package fr.uga.m1miage.pc.partie.controllers;

import fr.uga.m1miage.pc.mappers.GlobalMapper;
import fr.uga.m1miage.pc.partie.models.PartieEntity;
import fr.uga.m1miage.pc.partie.models.PartieJoueurEntity;
import fr.uga.m1miage.pc.partie.requests.CoupRequest;
import fr.uga.m1miage.pc.partie.responses.PartieDetailsDTO;
import fr.uga.m1miage.pc.partie.responses.PartieJoueurDTO;
import fr.uga.m1miage.pc.partie.services.PartieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/parties")
public class PartieController {


    private final PartieService partieService;


    public PartieController(PartieService partieService) {
        this.partieService = partieService;
    }

    @PostMapping("/{idJeu}/joueurs/{idJoueur}/jouer-coup")
    public ResponseEntity<PartieJoueurDTO> jouerCoup(
            @PathVariable Long idJeu,
            @PathVariable UUID idJoueur,
            @RequestBody CoupRequest coupRequest
    ) {
        PartieJoueurEntity partieJoueur = partieService.joueurCoup(idJoueur,idJeu,coupRequest.getCoup());
        PartieJoueurDTO partieJoueurDTO = GlobalMapper.INSTANCE.toDto(partieJoueur);
        return ResponseEntity.ok(partieJoueurDTO);
    }


    @GetMapping("{idPartie}/details")
    public ResponseEntity<PartieDetailsDTO> recupererDetailsPartie(@PathVariable UUID idPartie) {
        PartieEntity partie = partieService.recupererDetailsPartie(idPartie);
        PartieDetailsDTO response = GlobalMapper.INSTANCE.mapEntityToDetailsDTO(partie);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
