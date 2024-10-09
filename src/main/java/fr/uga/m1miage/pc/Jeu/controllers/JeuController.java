package fr.uga.m1miage.pc.Jeu.controllers;


import fr.uga.m1miage.pc.Jeu.mappers.JeuMapper;
import fr.uga.m1miage.pc.Jeu.models.JeuEntity;
import fr.uga.m1miage.pc.Jeu.requests.JeuConnexionRequestDTO;
import fr.uga.m1miage.pc.Jeu.requests.JeuCreationRequestDTO;
import fr.uga.m1miage.pc.Jeu.response.JeuConnexionResponseDTO;
import fr.uga.m1miage.pc.Jeu.response.JeuCreationResponseDTO;
import fr.uga.m1miage.pc.Jeu.service.JeuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jeux")
@Tag(name = "Jeu")
public class JeuController {
    @Autowired
    private JeuService jeuService;

    @Autowired
    private JeuMapper jeuMapper;


    @PostMapping("/creer-jeu")
    public ResponseEntity<JeuCreationResponseDTO> creerJeu(@RequestBody JeuCreationRequestDTO jeuCreationDTO) {
        JeuEntity jeu = jeuService.creerJeu(jeuCreationDTO.getPseudoJoueur(),jeuCreationDTO.getNombreParties());
        JeuCreationResponseDTO response = jeuMapper.mapJeuEntityToJeuCreationResponseDTO(jeu);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/joindre-jeu")
    public ResponseEntity<JeuConnexionResponseDTO> joindreJeu(@RequestBody JeuConnexionRequestDTO jeuConnexionDTO) {
      JeuEntity jeu =  jeuService.joindreJeu(jeuConnexionDTO.getPseudoJoueur(), jeuConnexionDTO.getCodeJeu());
        JeuConnexionResponseDTO response = jeuMapper.mapJeuEntityToJeuConnexionResponseDTO(jeu);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
