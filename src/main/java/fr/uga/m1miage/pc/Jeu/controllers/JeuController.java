package fr.uga.m1miage.pc.Jeu.controllers;


import fr.uga.m1miage.pc.Jeu.models.JeuEntity;
import fr.uga.m1miage.pc.Jeu.response.JeuCreationDTO;
import fr.uga.m1miage.pc.Jeu.service.JeuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jeu")
public class JeuController {

    @Autowired
    private JeuService jeuService;


    @PostMapping("/creerJeu")
    public ResponseEntity<JeuEntity> creerJeu(@RequestBody JeuCreationDTO jeuCreationDTO) {
        JeuEntity jeu = jeuService.creerJeu(jeuCreationDTO.getPseudoJoueur(),jeuCreationDTO.getNombreParties());
        return ResponseEntity.status(HttpStatus.CREATED).body(jeu);
    }

}
