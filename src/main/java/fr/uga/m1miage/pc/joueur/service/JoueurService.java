package fr.uga.m1miage.pc.joueur.service;


import fr.uga.m1miage.pc.joueur.enums.StrategieEnum;
import fr.uga.m1miage.pc.joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.joueur.repository.JoueurRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class JoueurService {


    private final JoueurRepository joueurRepository;
    public JoueurEntity abandonnerJeu(UUID idJoueur, StrategieEnum strategie) {
        JoueurEntity joueur = joueurRepository.findById(idJoueur).orElseThrow();
        joueur.setAbandon(true);
        joueur.setStrategie(strategie);
        return joueurRepository.save(joueur);
    }
}
