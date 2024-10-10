package fr.uga.m1miage.pc.Joueur.service;


import fr.uga.m1miage.pc.Joueur.enums.StrategieEnum;
import fr.uga.m1miage.pc.Joueur.models.JoueurEntity;
import fr.uga.m1miage.pc.Joueur.repository.JoueurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JoueurService {

    @Autowired
    JoueurRepository joueurRepository;
    public JoueurEntity abandonnerJeu(UUID idJoueur, StrategieEnum strategie) {
        JoueurEntity joueur = joueurRepository.findById(idJoueur).orElseThrow();
        joueur.setAbandon(true);
        joueur.setStrategie(strategie);
        return joueurRepository.save(joueur);
    }
}
